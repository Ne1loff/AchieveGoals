<script lang="ts">
    import ModalWindow from "../../modals/ModalWindow.svelte";
    import {links, navigate} from "svelte-routing";
    import {Breadcrumb, BreadcrumbItem, OverflowMenu, OverflowMenuItem} from "carbon-components-svelte";
    import {hrefs} from "../../../resources/config";
    import {onMount} from "svelte";
    import Task, {Parent} from "../../../data/models/Task";
    import {TASKS} from "../../../data/storage/storage";
    import ServiceFactory from "../../../services/ServiceFactory";
    import AppHeadTitle from "../../AppHeadTitle.svelte";
    import {getProjectPath} from "../../../utils/location-utils";
    import GoalInfoHolder from "./GoalInfoHolder.svelte";
    import SubtaskHolder from "./SubtaskHolder.svelte";
    import GoalInfoSidePanel from "./side-panel/GoalInfoSidePanel.svelte";
    import Button from "../../button/Button.svelte";
    import Icon from "@iconify/svelte";

    export let props: { id: string };

    let parents: { id: number | string, title: string }[] = [];
    let children: Task[] = [];
    let goal: Task | undefined;

    const navigateToHome = () => navigate(getProjectPath());

    let componentIsReady: boolean = false;

    const loadGoalAndParents = (): boolean => {
        const goals = $TASKS;
        goal = goals.find((it) => it.id === Number(props.id));

        let ok: boolean = false;

        const getParents = (gid: number): Parent[] => {
            ok = true;
            const parent = goals.find((it) => it.id === gid);

            if (!parent) {
                return [];
            } else if (parent.gid) {
                return [...getParents(parent.gid), {id: parent.id, title: parent.title}];
            } else {
                return [{id: parent.id, title: parent.title}];
            }
        }

        const getChildren = () => {
            if (!goal) return [];
            return goals.filter((it) => it.gid === goal.id);
        }

        parents = goal ? getParents(goal.gid) : [];
        children = goal ? getChildren() : [];
        return ok;
    }

    const loadData = (_: {}) => {
        ServiceFactory.INSTANCE.taskService.getUserGoals()
            .then(() => {
                componentIsReady = loadGoalAndParents();
                if (!componentIsReady) {
                    ServiceFactory.INSTANCE.toastService.error(
                        'Goal notfound!', `There is no target with id: ${props.id}`)
                }
            });
    }

    $: loadData(props);

    onMount(() => {
        loadData(props);
    });

</script>


<AppHeadTitle text={goal?.title ?? ''}/>
<ModalWindow on:close={navigateToHome} styleClasses={['goal-info', 'elevation-8']}
             showFooter={false}
             hideCloseBtn
             --menu-container-hr-margin="0 -.75rem"
             --own-modal-max-height="100vh"
             --own-modal-min-height="56rem"
             --own-modal-padding="2rem"
             --own-overlay-bg="#131313bf"
>
    <div class="modal-header-content" slot="header" use:links let:close>
        {#if componentIsReady}
            <!-- TODO: Написать свою реализацию Breadcrumb-->
            <Breadcrumb noTrailingSlash>
                <BreadcrumbItem href={hrefs.home}>Goals</BreadcrumbItem>
                {#if parents.length > 0}
                    <BreadcrumbItem>
                        <OverflowMenu>
                            {#each parents as parent}
                                <OverflowMenuItem href={hrefs.task(parent.id)}>{parent.title}</OverflowMenuItem>
                            {/each}
                        </OverflowMenu>
                    </BreadcrumbItem>
                {/if}
                <BreadcrumbItem href={hrefs.task(goal.id)} isCurrentPage>
                    {goal.title}
                </BreadcrumbItem>
            </Breadcrumb>
        {:else}
            <Breadcrumb noTrailingSlash skeleton count={2}/>
        {/if}
        <Button kind="ghost"
                size="small"
                --ag-bnt-border-radius=".5rem"
                --ag-bnt-padding="0"
                on:click={close}>
            <Icon icon="carbon:close" width="28"/>
        </Button>
    </div>
    <svelte:fragment slot="content">
        <div class="content">
            <div class="main-content">
                <GoalInfoHolder bind:goal />
                <SubtaskHolder goals={children} hideGoalCreator={parents.length === 4}/>
            </div>
            <div class="side-panel">
                <GoalInfoSidePanel bind:task={goal}/>
            </div>
        </div>
    </svelte:fragment>
</ModalWindow>

<style lang="scss">

  :global(.goal-info) {
    width: 54rem;
    border-radius: 16px;
    background-color: var(--cds-field);
  }

  .modal-header-content {
    width: 100%;
    height: 3rem;
    margin-bottom: -.5rem;

    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    padding-right: 1rem;
    padding-left: 1rem;
  }

  .content {
    display: flex;
    height: 100%;
  }

  .main-content {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    padding: .5rem .5rem .5rem 1.25rem;
    gap: 1rem;
  }

  .side-panel {
    width: 15rem;
  }

</style>