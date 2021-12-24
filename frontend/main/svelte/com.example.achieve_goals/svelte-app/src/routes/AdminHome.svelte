<script lang="ts">
    import {navigate} from "svelte-routing";
    import dayjs from "dayjs";
    import {writable} from "svelte/store";

    import 'dayjs/locale/ru'

    dayjs.locale('ru')

    import Scheduler from "./components/Scheduler.svelte";
    import EditGoal from "./components/EditGoal.svelte";
    import Icon from "@iconify/svelte"
    import Header from "./components/Header.svelte";
    import User from "./components/User.svelte";
    import Goal from "./components/Goal.svelte";
    import GoalInfo from "./components/GoalInfo.svelte";

    const inDev = () => {
        alert("В разработке)");
    }

    let sidebarActive = false;
    let activeBtn = false;
    let showGoals;

    const newGoal = writable({
        title: '',
        description: '',
        gid: null,
        priority: 4,
        deadline: new Date(),
        deadlineTime: ''
    })
    let create = true;

    let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
        admin: '',
        locality: '',
    };
    let goal = {
        id: 1,
        title: 'Убраться дома',
        description: '',
        isDone: false,
        gid: null,
        priority: 1,
        subtasks: {
            total: 1,
            completed: 1
        },
        createdAt: 11,
        updatedAt: 11,
        deadline: '11 январь'
    };
    let userGoals = [];
    let users = []
    let updatedGoals = [];
    let deleteGoals = [];

    let showSubtasks = {1: false}

    let showSetGoalWindow = false;
    let showScheduler = false;
    let bounding;

    let showFilters = false;
    let filtersIsActive;
    let filters = [
        {
            priority: 1, active: false, icon: "bi:flag-fill", color: "#de4c4a"
        },
        {
            priority: 2, active: false, icon: "bi:flag-fill", color: "#f49c18"
        },
        {
            priority: 3, active: false, icon: "bi:flag-fill", color: "#3077e1"
        },
        {
            priority: 4, active: false, icon: "bi:flag", color: ""
        },
    ]
    $: filtersIsActive = filters.filter(e => e.active).length != 0;

    let showGoalInfo = false;
    let infoAbout;

    const showInfo = (sub) => {
        showGoalInfo = true;
        infoAbout = sub;
    }

    const editGoal = (goal) => {
        $newGoal = JSON.parse(JSON.stringify(goal.detail));
        $newGoal.deadline = new Date($newGoal.deadline)
        showSetGoalWindow = true;
        create = false;
    }

    const createGoal = () => {
        showSetGoalWindow = true
    }

    const createSubtask = (id) => {
        $newGoal.gid = id.detail;
        showSetGoalWindow = true;
    }

    const clearNewGoal = () => {
        $newGoal['title'] = '';
        $newGoal['description'] = '';
        $newGoal['gid'] = null;
        $newGoal['priority'] = 4;
        $newGoal['deadline'] = new Date();
        $newGoal['deadlineTime'] = '';
    }

    const updateGoal = (g) => {
        let upd_goal = g.detail
        if (upd_goal.deadlineTime === '') upd_goal.deadlineTime = '23:59'
        upd_goal.deadline = dayjs(upd_goal.deadline)
            .set('h', Number(upd_goal.deadlineTime.substr(0, 2)))
            .set('m', Number(upd_goal.deadlineTime.substr(3, 2)))
            .toDate();
        updatedGoals = []
        updatedGoals.push(upd_goal)
        updateGoals()
    }

    const updateGoals = () => {
        fetch('/api/goals/', {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedGoals, (key, value) => key === 'subtasks' ? undefined : value)
        }).then(response => {
            if (response.status !== 202) alert('Не удалось обновить цель');
            getGoalsAndUsers();
        })
    }

    const update = () => {
        for (let i = 0; i < userGoals.length; i++) {
            userGoals[i].subtasks.completed = userGoals.filter(e => e.gid === userGoals[i].id && e.isDone).length;
            userGoals[i] = userGoals[i];
        }
    }

    const complete = (goal) => {
        updatedGoals = [];
        for (let i = 0; i < userGoals.length; i++) {
            if (userGoals[i].id === goal.detail.id) {
                if (goal.detail.isDone) {
                    completeSubs(goal.detail.id);
                } else {
                    uncompleteSubs(goal.detail.gid)
                }
                updatedGoals.push(userGoals[i]);
                update();
            }
        }
        updateGoals();
    }

    const completeSubs = (id) => {
        userGoals.filter(e => e.gid === id).forEach(e => {
            completeSubs(e.id);
            e.isDone = true;
            updatedGoals.push(e);
        })
    }

    const uncompleteSubs = (gid) => {
        userGoals.filter(e => e.id === gid).forEach(e => {
            if (e.isDone) {
                uncompleteSubs(e.gid);
                e.isDone = false;
                updatedGoals.push(e);
            }
        })
    }

    const deleteGoal = (goal) => {
        let id = goal.detail
        deleteGoals = []
        deleteGoals.push(userGoals.find(g => g.id === id).id)
        deleteGoals = deleteGoals.concat(findSubs(id))

        fetch('api/goals/', {
                method: 'DELETE',
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(deleteGoals)
            }
        ).then(response => {
            if (response.status !== 200) alert('Не удалось обновить цель');
            getGoalsAndUsers();
        })
    }

    const findSubs = (gid) => {
        let subs = []
        userGoals.filter(g => g.gid === gid).forEach(g => {
            subs.push(g.id)
            subs = subs.concat(findSubs(g.id))
        })
        return subs
    }

    const changeShowSub = (id) => {
        showSubtasks[id.detail] = !showSubtasks[id.detail];
    }

    const getGoalsAndUsers = () => {
        let currentLocation = window.location
        if (currentLocation.pathname == '/admin/goals') {
            fetch('/api/admin/goals')
                .then(response => {
                    if (response.status === 200)
                        return response.json()
                    else if (response.status === 403)
                        navigate('/home')
                    else
                        console.error("Can't upload goals")
                }).then(commit => {
                userGoals = commit;
                for (let i = 0; i < userGoals.length; i++) {
                    let userGoal = userGoals[i];
                    userGoal['subtasks'] = {
                        total: userGoals.filter(e => e.gid === userGoal.id).length,
                        completed: userGoals.filter(e => e.gid === userGoal.id && e.isDone).length,
                    }
                    let time = new Date(userGoal.deadline)
                    userGoal.deadline = time;
                    userGoal['deadlineTime'] = dayjs(time).format("HH:mm");
                    if (userGoal.subtasks.total > 0) {
                        if (!showSubtasks[userGoal.id]) showSubtasks[userGoal.id] = false;
                    }
                }
            })
        } else if (currentLocation.pathname == '/admin/users') {
            fetch('/api/admin/users')
                .then(response => {
                    if (response.status === 200) {
                        return response.json()
                    } else {
                        navigate('/home')
                    }
                }).then(commit => {
                users = commit;
                console.log(users)
            })
        }
    }

    $:{
        if (showGoals) {}
        getGoalsAndUsers()
    }

    fetch('/api/user')
        .then(response => {
            if (response.status === 200)
                return response.json()
            else {
                navigate('/login')
            }
        }).then(commit => {
        if (commit) {
            user = commit
            if (window.location.pathname == '/admin') {
                navigate('/admin/goals')
                showGoals = window.location.pathname == '/admin/goals'
            }
            getGoalsAndUsers()
        }
    })
</script>

<div id="app">
    <div class="root_page">
        <Header {user}/>
        <div class="root_wrapper">
            <div class="sidebar" on:mouseover={() => sidebarActive = true} on:mouseout={() => sidebarActive = false}>
                <div class="sidebar_buttons">
                    <button class="create_button" type="button" on:click={() => {
                        navigate('/admin/goals');
                        showGoals = true;
                    }}>
                        <div class="create_button-icon">
                            <Icon icon="eos-icons:package-upgrade-outlined"
                                  style="height: 24px; width: 24px; color: #000;"/>
                        </div>
                        <span class="create_button-title">
                                Цели
                        </span>
                    </button>
                    <button class="create_button" type="button" on:click={() => {
                        navigate('/admin/users');
                        showGoals = false;
                    }}>
                        <div class="create_button-icon">
                            <Icon icon="carbon:task-add" style="height: 24px; width: 24px; color: #000;"/>
                        </div>
                        <span class="create_button-title">
                                Пользователи
                        </span>
                    </button>
                </div>
                <div class="sidebar_navigation">
                    <div class="navigation_panel">
                        <div class="navigation_panel-header">
                            <button class="navigation_panel-header-btn" on:click|once={inDev}>
                                <Icon icon="uil:angle-right-b" style="width: 20px; height: 20px; margin-right: 4px;"/>
                                Проекты
                            </button>
                            <div class="navigation_panel-header-icon{sidebarActive ? '--active' : ''}">
                                <button class="navigation_panel-header-icon-btn">
                                    <Icon icon="akar-icons:plus"/>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="navigation_panel">
                        <div class="navigation_panel-header">
                            <button class="navigation_panel-header-btn" on:click|once={inDev}>
                                <Icon icon="uil:angle-right-b" style="width: 20px; height: 20px; margin-right: 4px;"/>
                                Метки
                            </button>
                            <div class="navigation_panel-header-icon{sidebarActive ? '--active' : ''}">
                                <button class="navigation_panel-header-icon-btn">
                                    <Icon icon="akar-icons:plus"/>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="navigation_panel">
                        <div class="navigation_panel-header">
                            <button class="navigation_panel-header-btn" on:click={() => showFilters = !showFilters}>
                                <Icon icon="uil:angle-right-b" style="width: 20px; height: 20px; margin-right: 4px;"
                                      rotate="{showFilters ? '45' : '0'}"/>
                                Фильтры
                            </button>
                            <div class="navigation_panel-header-icon{sidebarActive ? '--active' : ''}">
                                <button class="navigation_panel-header-icon-btn">
                                    <Icon icon="akar-icons:plus"/>
                                </button>
                            </div>
                        </div>
                        {#if showFilters}
                            <div class="filter-container">
                                {#each filters as filter}
                                    <button class="filter-btn{filter.active ? '--active' : ''}"
                                            on:click={() => filter.active = !filter.active}>
                                        <Icon icon={filter.icon}
                                              style="width: 20px; height: 20px; color: {filter.color};"/>
                                        <span>{"Приоритет - " + filter.priority}</span>
                                    </button>
                                {/each}
                            </div>
                        {/if}
                    </div>
                </div>
                <div class="sidebar_app-link-wrapper">
                    <div class="app-link-wrapper-container">
                        <Icon icon="fluent:phone-laptop-24-regular" style="width: 24px; height: 24px; color: #000"/>
                        <a href="#" on:click|once={inDev}>Установить приложение</a>
                    </div>
                </div>
            </div>
            <div class="root_content">
                <div class="root_content-inner">
                    <div class="root_content-container">
                        <div class="container-header">
                            <div class="header-title">
                                <h1 class="title-text">{showGoals ? "Цели" : "Пользователи"}</h1>
                            </div>
                        </div>
                        <div class="container-content-list">
                            <div class="content-list-inner">
                                {#if showGoals}
                                    {#if filtersIsActive}
                                        {#each userGoals.filter(e => filters.filter(e => e.active).map(e => e.priority).includes(e.priority) && !e.isDone) as goal}
                                            <Goal {goal}
                                                  showSub={false}
                                                  on:showSub={changeShowSub}
                                                  on:done={complete}
                                                  on:createGoal={createGoal}
                                                  on:createSub={createSubtask}
                                                  on:click={showInfo(goal)}
                                                  on:edit={editGoal}
                                                  on:update={updateGoal}
                                                  on:delete={deleteGoal}
                                            />
                                        {/each}

                                        <div class="children-content-add"
                                             on:mouseenter={() => activeBtn = true}
                                             on:mouseleave={() => activeBtn = false}>
                                            <button class="content-add-btn{activeBtn ? '--active' : ''}"
                                                    on:click={createGoal}>
                                                <Icon icon="{activeBtn ? 'bi:plus-circle-fill' : 'bi:plus'}"
                                                      style="color: #f00; width: 24px; height: 24px"/>
                                                <span>Добавить цель</span>
                                            </button>
                                        </div>

                                        {#each userGoals.filter(e => filters.filter(e => e.active).map(e => e.priority).includes(e.priority) && e.isDone) as goal }
                                            <Goal {goal}
                                                  showSub={false}
                                                  on:showSub={changeShowSub}
                                                  on:done={complete}
                                                  on:createGoal={createGoal}
                                                  on:createSub={createSubtask}
                                                  on:click={showInfo(goal)}
                                                  on:edit={editGoal}
                                                  on:update={updateGoal}
                                                  on:delete={deleteGoal}
                                            />
                                        {/each}
                                    {:else}
                                        {#each userGoals.filter(e => !e.gid && !e.isDone) as goal}
                                            <Goal {goal}
                                                  on:showSub={changeShowSub}
                                                  on:done={complete}
                                                  on:createGoal={createGoal}
                                                  on:createSub={createSubtask}
                                                  on:click={showInfo(goal)}
                                                  on:edit={editGoal}
                                                  on:update={updateGoal}
                                                  on:delete={deleteGoal}
                                            />
                                            {#if showSubtasks[goal.id]}
                                                {#each userGoals.filter(e => e.gid === goal.id) as subtask_1}
                                                    <Goal goal="{subtask_1}" indent="2"
                                                          on:showSub={changeShowSub}
                                                          on:done={complete}
                                                          on:createGoal={createGoal}
                                                          on:createSub={createSubtask}
                                                          on:click={showInfo(subtask_1)}
                                                          on:edit={editGoal}
                                                          on:update={updateGoal}
                                                          on:delete={deleteGoal}
                                                    />
                                                    {#if showSubtasks[subtask_1.id]}
                                                        {#each userGoals.filter(e => e.gid === subtask_1.id) as subtask_2}
                                                            <Goal goal="{subtask_2}" indent="3"
                                                                  on:showSub={changeShowSub}
                                                                  on:done={complete}
                                                                  on:createGoal={createGoal}
                                                                  on:createSub={createSubtask}
                                                                  on:click={showInfo(subtask_2)}
                                                                  on:edit={editGoal}
                                                                  on:update={updateGoal}
                                                                  on:delete={deleteGoal}
                                                            />
                                                            {#if showSubtasks[subtask_2.id]}
                                                                {#each userGoals.filter(e => e.gid === subtask_2.id) as subtask_3}
                                                                    <Goal goal="{subtask_3}" indent="4"
                                                                          on:showSub={changeShowSub}
                                                                          on:done={complete}
                                                                          on:createGoal={createGoal}
                                                                          on:createSub={createSubtask}
                                                                          on:click={showInfo(subtask_3)}
                                                                          on:edit={editGoal}
                                                                          on:update={updateGoal}
                                                                          on:delete={deleteGoal}
                                                                    />
                                                                    {#if showSubtasks[subtask_3.id]}
                                                                        {#each userGoals.filter(e => e.gid === subtask_3.id) as subtask_4}
                                                                            <Goal goal="{subtask_4}" indent="5"
                                                                                  on:done={complete}
                                                                                  on:click={showInfo(subtask_4)}
                                                                                  on:createGoal={createGoal}
                                                                                  on:edit={editGoal}
                                                                                  on:update={updateGoal}
                                                                                  on:delete={deleteGoal}
                                                                            />
                                                                        {/each}
                                                                    {/if}
                                                                {/each}
                                                            {/if}
                                                        {/each}
                                                    {/if}
                                                {/each}
                                            {/if}
                                        {/each}

                                        <div class="children-content-add"
                                             on:mouseenter={() => activeBtn = true}
                                             on:mouseleave={() => activeBtn = false}>
                                            <button class="content-add-btn{activeBtn ? '--active' : ''}"
                                                    on:click={createGoal}>
                                                <Icon icon="{activeBtn ? 'bi:plus-circle-fill' : 'bi:plus'}"
                                                      style="color: #f00; width: 24px; height: 24px"/>
                                                <span>Добавить цель</span>
                                            </button>
                                        </div>

                                        {#each userGoals.filter(e => !e.gid && e.isDone) as goal}
                                            <Goal {goal}
                                                  on:showSub={changeShowSub}
                                                  on:done={complete}
                                                  on:createGoal={createGoal}
                                                  on:createSub={createSubtask}
                                                  on:click={showInfo(goal)}
                                                  on:edit={editGoal}
                                                  on:update={updateGoal}
                                                  on:delete={deleteGoal}
                                            />
                                            {#if showSubtasks[goal.id]}
                                                {#each userGoals.filter(e => e.gid === goal.id) as subtask_1}
                                                    <Goal goal="{subtask_1}" indent="2"
                                                          on:showSub={changeShowSub}
                                                          on:done={complete}
                                                          on:createGoal={createGoal}
                                                          on:createSub={createSubtask}
                                                          on:click={showInfo(subtask_1)}
                                                          on:edit={editGoal}
                                                          on:update={updateGoal}
                                                          on:delete={deleteGoal}
                                                    />
                                                    {#if showSubtasks[subtask_1.id]}
                                                        {#each userGoals.filter(e => e.gid === subtask_1.id) as subtask_2}
                                                            <Goal goal="{subtask_2}" indent="3"
                                                                  on:showSub={changeShowSub}
                                                                  on:done={complete}
                                                                  on:createGoal={createGoal}
                                                                  on:createSub={createSubtask}
                                                                  on:click={showInfo(subtask_2)}
                                                                  on:edit={editGoal}
                                                                  on:update={updateGoal}
                                                                  on:delete={deleteGoal}
                                                            />
                                                            {#if showSubtasks[subtask_2.id]}
                                                                {#each userGoals.filter(e => e.gid === subtask_2.id) as subtask_3}
                                                                    <Goal goal="{subtask_3}" indent="4"
                                                                          on:showSub={changeShowSub}
                                                                          on:done={complete}
                                                                          on:createGoal={createGoal}
                                                                          on:createSub={createSubtask}
                                                                          on:click={showInfo(subtask_3)}
                                                                          on:edit={editGoal}
                                                                          on:update={updateGoal}
                                                                          on:delete={deleteGoal}
                                                                    />
                                                                    {#if showSubtasks[subtask_3.id]}
                                                                        {#each userGoals.filter(e => e.gid === subtask_3.id) as subtask_4}
                                                                            <Goal goal="{subtask_4}" indent="5"
                                                                                  on:done={complete}
                                                                                  on:click={showInfo(subtask_4)}
                                                                                  on:createGoal={createGoal}
                                                                                  on:edit={editGoal}
                                                                                  on:update={updateGoal}
                                                                                  on:delete={deleteGoal}
                                                                            />
                                                                        {/each}
                                                                    {/if}
                                                                {/each}
                                                            {/if}
                                                        {/each}
                                                    {/if}
                                                {/each}
                                            {/if}
                                        {/each}
                                    {/if}
                                {:else}
                                    {#each users as user}
                                        <User {user}/>
                                    {/each}
                                {/if}
                            </div>
                        </div>
                    </div>
                </div>
                <footer class="footer">
                    <div></div>
                </footer>
            </div>
        </div>
    </div>
</div>
<div class="popper__overlay">
    <div class="goal-info__popper">
        {#if showGoalInfo}
            <GoalInfo bind:showInfo={showGoalInfo} bind:goal={infoAbout} bind:userGoals
                      on:done={complete}
                      on:createGoal={createGoal}
                      on:createSub={createSubtask}
                      on:edit={editGoal}
                      on:update={updateGoal}
                      on:delete={deleteGoal}
            />
        {/if}
    </div>
    <div class="set__goal__window__popper">
        {#if showSetGoalWindow}
            <EditGoal bind:showSetGoalWindow bind:showScheduler bind:bounding bind:goal={$newGoal} bind:create
                      on:clear={clearNewGoal} on:getGoals={getGoalsAndUsers} on:update={updateGoal}/>
        {/if}
    </div>
    <div class="scheduler__popper">
        {#if showScheduler}
            <Scheduler bind:goal={$newGoal} bind:bounding isCreate={create}
                       on:close={() => showScheduler = false}/>
        {/if}
    </div>
</div>

<style>

    button {
        margin: 0;
    }

    #app {
        min-height: 100vh;
        background: #F8F8F8;
    }

    .root_page {
        height: 100vh;

        display: flex;
        justify-content: space-between;
    }

    .root_wrapper {
        position: absolute;

        display: flex;

        bottom: 0;

        height: calc(100vh - 4rem - 5px);
        width: 100%;
    }

    .sidebar {
        display: flex;
        flex-direction: column;
        justify-content: space-between;

        height: 100%;
        width: 16rem;
    }

    .sidebar_buttons {
        margin: 32px auto;
        padding-top: 1px;

        display: flex;
        flex-direction: column;
        justify-content: space-between;

        height: 90px;
    }

    .create_button {
        height: 40px;
        width: 200px;
        border-radius: 5px;
        display: flex;
        align-items: center;
        justify-content: flex-start;
        margin: 0;
        border: none;
        padding: 0;
        font-size: 16px;
        font-weight: 700;
        background: #dcdcdc;
    }

    .create_button:hover {
        background: #b4b4b4;
    }

    .create_button-icon {
        padding: 0;
        margin-left: 8px;
    }

    .create_button-title {
        margin-left: 16px;
    }

    .sidebar_navigation {
        margin: 0 28px;
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: start;
    }

    .navigation_panel {
        margin-top: 12px;
        width: 100%;
        border: none;
    }

    .navigation_panel-header {
        display: flex;
        align-items: center;
        cursor: pointer;
        height: 36px;
    }

    .navigation_panel-header-btn {
        display: flex;
        align-items: center;
        flex: 1;

        margin: 0;
        background: #f8f8f8;
        border: none;
        cursor: pointer;

        text-align: left;
        font-size: 14px;
        color: #333;
        font-weight: 700;
        padding: 10px 0;
    }

    .filter-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;

        width: 100%;
    }

    .filter-btn, .filter-btn--active {
        padding: 8px;
        margin: 0 12px 0 0;

        width: calc(100% - 12px);

        display: flex;
        flex-direction: row;
        align-items: start;
        justify-content: space-around;

        background: #F8F8F8;
        border: none;
        border-radius: 8px;
        cursor: pointer;
    }

    .filter-btn--active {
        background: #7db8ef;
    }

    .filter-btn span, .filter-btn--active span {
        margin-left: 8px;
    }

    .filter-btn:hover {
        background: #dddddd;
    }

    .navigation_panel-header-icon, .navigation_panel-header-icon--active {
        display: flex;
        align-items: center;
        opacity: 1;
    }

    .navigation_panel-header-icon {
        opacity: 0;
    }

    .navigation_panel-header-icon-btn {
        height: 24px;
        width: 24px;

        padding: 0;
        margin: 0;

        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 3px;

        background: #f8f8f8;
        border: none;
        cursor: pointer;
    }

    .navigation_panel-header-icon-btn:hover {
        background: #f0f0f0;
    }

    .sidebar_app-link-wrapper {
        width: 100%;
        display: flex;
        align-content: center;
        justify-content: center;
    }

    .app-link-wrapper-container {
        display: flex;
        align-items: center;
    }

    .app-link-wrapper-container a {
        text-decoration: none;
        color: #3a3a3a
    }

    .app-link-wrapper-container a:hover {
        color: #000;
    }

    .root_content {
        height: 100%;
        width: calc(100% - 256px);
        overflow-y: auto;
    }

    .root_content-inner {
        min-height: calc(100% - 4rem - 8px);
        min-width: calc(460px + 64px);
        margin-top: 8px;
        margin-right: 2rem;
        margin-left: 4px;
        border-radius: 4px;
        background: #fff;
        box-shadow: 0 1px 5px rgb(0 0 0 / 21%);
    }

    .root_content-container {
        padding: 0 32px 32px;
        height: 100%;
        width: 100%;
        display: flex;
        box-sizing: border-box;
        flex-direction: column;
    }

    .container-header {
        display: flex;
        justify-content: center;
        align-items: center;

        padding-top: 36px;

        width: 100%;
        height: 34px;
    }

    .header-title {
        max-width: 800px;
        width: 800px;

        display: flex;
        justify-content: flex-start;
    }

    .title-text {
        font-weight: 700;
        font-size: 20px;

        margin: 0;
        padding: 0;
    }

    .container-content-list {
        width: 100%;

        padding-left: 20px;
        padding-right: 40px;
        box-sizing: border-box;

        display: flex;
        justify-content: center;
        align-items: center;
    }

    .content-list-inner {
        width: 100%;
        max-width: 800px;

        display: flex;
        justify-content: flex-start;
        flex-direction: column;
    }

    .content-add-btn, .content-add-btn--active {
        background: #fff;
        border: none;
        display: flex;
        align-items: center;

        font-weight: 300;
    }

    .content-add-btn span, .content-add-btn--active span {
        margin-left: 8px;
    }

    .content-add-btn--active {
        color: #f00;
    }

    .footer {
        height: 4rem;
        width: 100%;
    }

    /*TODO*/
    @media (max-width: 815px) {
    }

    @media (max-height: 550px) {
    }

</style>