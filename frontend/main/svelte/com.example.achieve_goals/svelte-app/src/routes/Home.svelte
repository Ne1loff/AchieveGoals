<script>
    import {navigate} from "svelte-routing";
    import dayjs from 'dayjs';

    import {DatePicker, localeFromDateFnsLocale} from 'date-picker-svelte'
    import {ru} from 'date-fns/locale'

    import Icon from "@iconify/svelte"
    import Header from "./components/Header.svelte";
    import Goal from "./components/Goal.svelte";
    import SetGoalWindow from "./MainComponents/SetGoalWindow.svelte";
    import SchedulerWindow from "./MainComponents/SchedulerWindow.svelte";
    import TextAreaViaAutosize from "./components/TextAreaViaAutosize.svelte";

    const daysName = (dayNum) => {
        let days = ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'];
        return days[dayNum];
    }

    console.log(daysName(dayjs().add(1, 'week').day()))

    const dates = {
        today: new Date(),
        todayNum: dayjs().date(),
        tomorrow: dayjs().add(1, 'day').toDate(),
        weekends: dayjs().set('day', 6).toDate(),
        nextWeek: dayjs().add(1, 'week').toDate()
    }

    let locale = localeFromDateFnsLocale(ru)

    const newGoal = {
        title: '',
        description: '',
        gid: null,
        priority: '',
        deadline: new Date(),
        deadlineTime: ''
    }

    let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
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
    let userGoals = [goal];
    let updatedGoals = [];

    let showSubtasks = {1: false}

    let showSetGoalWindow = false;
    let showScheduler = false;

    const createSubtask = (id) => {
        newGoal.gid = id.detail;
        showSetGoalWindow = true;
    }

    const clearNewGoal = () => {
        newGoal['title'] = '';
        newGoal['description'] = '';
        newGoal['gid'] = null;
        newGoal['priority'] = '';
        newGoal['deadline'] = new Date();
        newGoal['deadlineTime'] = '';
    }

    const setGoal = () => {
        let regx = new RegExp(' p[1-4]', 'gm');
        let match = regx.exec(newGoal.title);
        newGoal.priority = match ? match[0].substr(2, 1) : 4;
        newGoal.title = newGoal.title.slice(0, match ? match.index : newGoal.title.length);
        if (newGoal.deadlineTime === '') newGoal.deadlineTime = '23:59'
        newGoal.deadline = dayjs(newGoal.deadline)
            .set('h', Number(newGoal.deadlineTime.substr(0, 2)))
            .set('m', Number(newGoal.deadlineTime.substr(3, 2)))
            .toDate();

        showSetGoalWindow = false;

        let rest = newGoal.gid ? '/api/goals/sub-goals' : '/api/goals/';

        fetch(rest, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newGoal, (key, value) => key === 'deadlineTime' ? undefined : value)
        }).then(response => {
            if (response.status !== 201) alert('Goal doesn\'t set');
            getGoals();
        })

        clearNewGoal();
    }

    const updateGoals = () => {
        //console.log(JSON.stringify(updatedGoals, (key, value) => key === 'subtasks' ? undefined : value))
        fetch('/api/goals/', {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedGoals, (key, value) => key === 'subtasks' ? undefined : value)
        }).then(response => {
            if (response.status !== 202) alert('Goal doesn\'t set');
            getGoals();
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

    const changeShowSub = (id) => {
        showSubtasks[id.detail] = !showSubtasks[id.detail];
    }

    const getGoals = () => {
        fetch('http://localhost:8080/api/goals/')
            .then(response => {
                if (response.status === 200)
                    return response.json()
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
                if (userGoal.subtasks.total > 0) {
                    if (!showSubtasks[userGoal.id]) showSubtasks[userGoal.id] = false;
                }
            }
        })
    }

    fetch('http://localhost:8080/api/user')
        .then(response => {
            if (response.status === 200)
                return response.json()
            else {
                navigate('/login')
            }
        }).then(commit => {
        user = commit
    }).then(_ => {
        getGoals();
    })
</script>

<div id="app">
    <div class="root_page">
        <Header {user}/>
        <div class="root_wrapper">
            <div class="sidebar">
                <div class="sidebar_buttons">
                    <div class="create_goal_button">
                        <div class="button_icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-journal-plus" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M8 5.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V10a.5.5 0 0 1-1 0V8.5H6a.5.5 0 0 1 0-1h1.5V6a.5.5 0 0 1 .5-.5z"/>
                                <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                                <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                            </svg>
                        </div>
                        <div class="button_text">
                            <div class="create_goal">
                                <button class="create_button" type="button" on:click={() => showSetGoalWindow = true}>
                                    Create goal
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="create_task_button">
                        <div class="button_icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-journal-plus" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M8 5.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V10a.5.5 0 0 1-1 0V8.5H6a.5.5 0 0 1 0-1h1.5V6a.5.5 0 0 1 .5-.5z"/>
                                <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                                <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                            </svg>
                        </div>
                        <div class="button_text">
                            <div class="create_goal">
                                <button class="create_button" type="button">Create goal</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="sidebar_navigation">
                    <div>OLa</div>
                    <div>OLa</div>
                    <div>OLa</div>
                    <div>OLa</div>
                </div>
                <div class="sidebar_app-link-wrapper">
                    <a href="https://google.com">Hello world!</a>
                </div>
            </div>
            <div class="root_content">
                <div class="root_content-inner">
                    <div class="root_content-container">
                        <div class="container-header">
                            <div class="header-title">
                                <h1 class="title-text">Цели</h1>
                            </div>
                        </div>
                        <div class="container-content-list">
                            <div class="content-list-inner">
                                {#each userGoals.filter(e => !e.gid) as goal}
                                    <Goal {goal} on:showSub={changeShowSub} on:done={complete}
                                          on:createSub={createSubtask}/>
                                    {#if showSubtasks[goal.id]}
                                        {#each userGoals.filter(e => e.gid === goal.id) as subtask_1}
                                            <Goal goal="{subtask_1}" indent="2" on:showSub={changeShowSub}
                                                  on:done={complete} on:createSub={createSubtask}/>
                                            {#if showSubtasks[subtask_1.id]}
                                                {#each userGoals.filter(e => e.gid === subtask_1.id) as subtask_2}
                                                    <Goal goal="{subtask_2}" indent="3" on:showSub={changeShowSub}
                                                          on:done={complete} on:createSub={createSubtask}/>
                                                    {#if showSubtasks[subtask_2.id]}
                                                        {#each userGoals.filter(e => e.gid === subtask_2.id) as subtask_3}
                                                            <Goal goal="{subtask_3}" indent="4"
                                                                  on:showSub={changeShowSub} on:done={complete}
                                                                  on:createSub={createSubtask}/>
                                                            {#if showSubtasks[subtask_3.id]}
                                                                {#each userGoals.filter(e => e.gid === subtask_3.id) as subtask_4}
                                                                    <Goal goal="{subtask_4}" on:done={complete}
                                                                          indent="5"/>
                                                                {/each}
                                                            {/if}
                                                        {/each}
                                                    {/if}
                                                {/each}
                                            {/if}
                                        {/each}
                                    {/if}
                                {/each}
                            </div>
                        </div>
                    </div>
                </div>
                <footer class="footer">
                    <div>
                        Some info!
                    </div>
                </footer>
            </div>
        </div>
    </div>
</div>
<div class="popper__overlay">
    <div class="set__goal__window__popper">
        {#if showSetGoalWindow}
            <SetGoalWindow on:close="{() => {showSetGoalWindow = false; clearNewGoal();}}"
                           bind:anotherModal={showScheduler}>
                <div class="set-goal-window-main">
                    <div class="set-goal-window-main-inner">
                        <input class="set-goal-window-input" type="text" placeholder="Цель"
                               bind:value={newGoal.title}/>
                        <TextAreaViaAutosize bind:value={newGoal.description}
                                             placeholder="Описание"
                                             minRows="2"/>
                    </div>
                    <div class="set-goal-window-buttons">
                        <button class="calendar_button" type="button" on:click={() => showScheduler = true}>
                            <Icon class="calendar_button_icon" icon="bi:calendar-week"/>
                            <span class="calendar_button_span">{dayjs(newGoal.deadline).format('DD ddd ') + newGoal.deadlineTime}</span>
                        </button>
                    </div>
                </div>
                <button class="set_goal_button" slot="button" disabled={newGoal.title === ''} on:click={setGoal}>
                    Поставить цель
                </button>
            </SetGoalWindow>
        {/if}
    </div>
    <div class="scheduler__popper">
        {#if showScheduler}
            <SchedulerWindow on:close="{() => showScheduler = false}">
                <div class="scheduler-title" slot="header">
                    <span>{dayjs(newGoal.deadline).format('DD ddd ') + newGoal.deadlineTime}</span>
                </div>
                <div class="scheduler-suggestion" slot="suggestion">
                    <button class="scheduler-suggestion-item" on:click={() => newGoal.deadline = dates.today}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="bi:calendar" style="width: 18px; height: 18px; color: #058527;"/>
                            <span>{dates.todayNum}</span>
                        </span>
                        <span class="scheduler-suggestion-item-label">Сегодня</span>
                        <span class="scheduler-suggestion-item-weekend">{daysName(dayjs().day())}</span>
                    </button>
                    <button class="scheduler-suggestion-item" on:click={() => newGoal.deadline = dates.tomorrow}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="bi:sun" style="width: 18px; height: 18px; color: #ad6200;"/>
                        </span>
                        <span class="scheduler-suggestion-item-label">Завтра</span>
                        <span class="scheduler-suggestion-item-weekend">{daysName(dayjs().add(1, 'day').day())}</span>
                    </button>
                    <button class="scheduler-suggestion-item" on:click={() => newGoal.deadline = dates.weekends}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="mdi:sofa-outline" style="width: 18px; height: 18px; color: #246fe0;"/>
                        </span>
                        <span class="scheduler-suggestion-item-label">На выходных</span>
                        <span class="scheduler-suggestion-item-weekend">Сб</span>
                    </button>
                    <button class="scheduler-suggestion-item" on:click={() => newGoal.deadline = dates.nextWeek}>
                        <span class="scheduler-suggestion-item-icon">
                            <Icon icon="gg:calendar-next" style="width: 18px; height: 18px; color: #692fc2;"/>
                        </span>
                        <span class="scheduler-suggestion-item-label">След.неделя</span>
                        <span class="scheduler-suggestion-item-weekend">{daysName(dayjs().add(1, 'week').day())}</span>
                    </button>
                </div>
                <div class="scheduler-date-picker" slot="date">
                    <DatePicker bind:value={newGoal.deadline} {locale} min="{dayjs().toDate()}"/>
                </div>
                <div class="scheduler-action" slot="time">
                    <span class="scheduler-action-label">Время</span>
                    <input type="time" bind:value={newGoal.deadlineTime}/>
                </div>
            </SchedulerWindow>
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
        margin: 1px 32px 10px 8px;
        padding-top: 1px;

        display: flex;
        flex-direction: column;
        justify-content: space-between;

        height: 90px;
        width: calc(186px - 16px);
    }

    .create_goal_button, .create_task_button {
        width: 100%;
        border-radius: 5px;

        display: flex;
        align-items: center;
        justify-content: center;

        height: 40px;
        margin: 8px 0;
        background: #f4f4f4;
    }

    .button_icon {
        justify-content: center;
        align-items: center;
        z-index: 1000;
        display: block;
    }

    .button_text {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 70%;
    }

    .create_goal {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .create_button {
        height: 40px;
        width: 186px;
        margin: 0;
        border: none;
        padding: 0;
        font-size: 20px;
        font-weight: 500;
    }

    .set-goal-window-main {
        background: white;
        border-radius: 10px;
        margin-bottom: 16px;
    }

    .set-goal-window-main-inner {
        max-height: 200px;
        overflow-x: hidden;
        overflow-y: auto;
        margin-bottom: 10px;
    }

    .set-goal-window-input {
        margin: 0 0 8px 0;
        padding: 0;
        width: 100%;
        border: none;
    }

    .set-goal-window-input:focus {
        outline: none;
    }

    .set-goal-window-buttons {
        display: flex;
    }

    .calendar_button {
        background: #fff;
        font-size: 1.2em;
        cursor: pointer;

        height: 28px;

        display: flex;
        flex-shrink: 0;
        align-items: center;
        color: #555;

        border: 1px solid #ccc;
        border-radius: 5px;

        padding: 0 8px;
    }

    .calendar_button:hover {
        background: #eee;
    }

    :global(.calendar_button_icon) {
        width: 16px;
        height: 16px;
    }

    .calendar_button_span {
        margin: 0 8px;
    }

    .set_goal_button {
        margin: 12px 0 0 12px;
        background: #db4c3f;
        border: 1px solid rgba(0, 0, 0, .1);

        line-height: 18px;
        padding: 6px 10px;
        height: 32px;

        font-weight: 500;
        font-size: 14px;
        border-radius: 5px;
        box-sizing: border-box;

        color: white;
    }

    .set_goal_button:disabled {
        opacity: 0.4;
        cursor: not-allowed;
    }

    .root_content {
        height: 100%;
        width: calc(100% - 256px);
        overflow-y: scroll;
    }

    .root_content-inner {
        min-height: calc(100% - 4rem - 8px);
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

        display: flex;
        justify-content: center;
        align-items: center;
    }

    .content-list-inner {
        display: flex;
        justify-content: flex-start;
        flex-direction: column;
    }

    .scheduler-title {
        display: flex;
        align-items: center;
        justify-content: flex-start;

        padding: 8px 10px;;
    }

    .scheduler-suggestion {
        display: flex;
        flex-direction: column;

        padding: 4px 0;
    }

    .scheduler-suggestion-item {
        border: 0;
        background: none;
        display: flex;
        flex-direction: row;
        align-items: center;

        padding: 4px 10px;
        line-height: 24px;
        outline: none;

        width: 100%;
    }

    .scheduler-suggestion-item:hover {
        background: #f1f1f1;
    }

    .scheduler-suggestion-item-icon {
        display: flex;
        position: relative;
        margin-right: 10px;
        color: grey;
    }

    .scheduler-suggestion-item-icon span {
        width: 9px;
        height: 9px;

        position: absolute;
        transform: translate(3.5px, -1px);

        color: #058527;
        font-size: 10px;
        font-weight: 500;
    }

    .scheduler-suggestion-item-label {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        margin-right: 4px;

        font-size: 13px;
        font-weight: 400;
        color: #202020;
    }

    .scheduler-suggestion-item-weekend {
        margin-left: auto;
        color: grey;
    }

    .scheduler-date-picker {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    :root {
        --date-picker-highlight-border: none;
        --date-picker-highlight-shadow: none;
    }

    .scheduler-action {
        display: flex;
        align-items: center;
        justify-content: space-between;

        padding: 8px 10px;
    }

    .scheduler-action-label {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        margin-right: 4px;

        font-size: 14px;
        font-weight: 400;
        color: #202020;
    }

    .scheduler-action input {
        line-height: 22px;
        padding: 0 4px;
        margin: 0;
        border-radius: 3px;
        border: 1px solid #ccc;
        outline: none;
        box-sizing: border-box;
    }

    .footer {
        height: 4rem;
        width: 100%;
    }

</style>