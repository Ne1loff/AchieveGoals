<script lang="ts">
    import ModalWindow from "../modals/ModalWindow.svelte";
    import GoalsHolder from "../home-components/ProjectTaskHolder.svelte";
    import {links, navigate} from "svelte-routing";
    import {Breadcrumb, BreadcrumbItem, OverflowMenu, OverflowMenuItem} from "carbon-components-svelte";
    import {hrefs} from "../../resources/config";
    import {onMount} from "svelte";
    import Goal, {Parent} from "../../data/models/Goal";
    import {GOALS} from "../../data/storage/storage";
    import ServiceFactory from "../../services/ServiceFactory";
    import GoalEditorComponent from "./GoalEditorComponent.svelte";
    import AppHeadTitle from "../AppHeadTitle.svelte";
    import {getProjectPath} from "../../utils/location-utils";

    export let props: { id: string };

    let parents: { id: number | string, title: string }[] = [];
    let children: Goal[] = [];
    let goal: Goal | undefined = undefined;

    const navigateToHome = () => navigate(getProjectPath());

    let componentIsReady: boolean = false;

    const loadGoalAndParents = (): boolean => {
        const goals = $GOALS;
        goal = goals.find((it) => it.id === Number(props.id));

        let ok: boolean = false;

        const getParents = (gid: number): Parent[] => {
            ok = true;
            const parent = goals.find((it) => it.id === gid);

            if (parent === undefined) {
                return [];
            } else if (parent.gid) {
                return [...getParents(parent.gid), {id: parent.id, title: parent.title}];
            } else {
                return [{id: parent.id, title: parent.title}];
            }
        }

        const getChildren = () => {
            return goals.filter((it) => it.gid === goal.id);
        }

        parents = goal ? getParents(goal.gid) : [];
        children = goal ? getChildren() : [];
        return ok;
    }

    const loadData = (_: {}) => {
        ServiceFactory.INSTANCE.goalService.getUserGoals()
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
             --menu-container-hr-margin="0 -.75rem"
             --own-modal-max-height="100vh"
             --own-modal-min-height="56rem"
             --own-overlay-bg="#131313bf"
>
    <div slot="header" use:links>
        {#if componentIsReady}
            <!--TODO: Написать свою реализацию Breadcrumb-->
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
    </div>
    <svelte:fragment slot="title">
        <div class="content">
            <div class="main-content">
                <GoalEditorComponent {goal}/>
                <GoalsHolder goals={children}
                             withoutHeader
                             collapsible
                             goalProps={{withoutSubs: true}}
                             goalStyle={[
                                 "--own-component-border-width: calc(100% + 8px);",
                                 "--own-component-border-margin : 0 -38px 0 0;"
                                 ]}
                             --holder-padding-right="38px"
                             --holder-padding-left="0"
                />
            </div>
            <div class="side-panel">

            </div>
        </div>
    </svelte:fragment>
</ModalWindow>

<style>

    :global(.goal-info) {
        width: 54rem;
        padding: .75rem;

        border: 1px solid var(--cds-border-subtle);
        border-radius: 16px;

        background-color: var(--cds-field);
    }

    .content {
        display: flex;
    }

    .main-content {
        flex-grow: 1;
    }

    .side-panel {
        width: 15rem;
    }

</style>