<script lang="ts">
    import ProjectTaskHolder from "../../components/home-components/ProjectTaskHolder.svelte";
    import GoalInfoComponent from "../../components/goal-card/GoalInfoComponent.svelte";
    import HomePage from "../HomePage.svelte";
    import ValFromParams from "../../components/ValFromParams.svelte";
    import {GOALS} from "../../data/storage/storage";
    import {Route, Router} from "svelte-routing";
    import TodayTaskHolder from "../../components/home-components/TodayTaskHolder.svelte";

    let tabParams;

</script>


<HomePage params={tabParams}>
    <svelte:fragment slot="content">
        <Router>
            <!--            <Route path="project/:projectId/*" let:params> TODO: PROJECTS -->
            <!--                <ValFromParams params={{tab: 'goals'}} bind:value={tabParams}/>-->
            <!--                <ProjectTaskHolder goals={$GOALS.filter(it => !it.gid)}/>-->
            <!--                <Router>-->
            <!--                    <Route path="task/:id" let:params>-->
            <!--                        <GoalInfoComponent props={params}/>-->
            <!--                    </Route>-->
            <!--                </Router>-->
            <!--            </Route>-->
            <Route path=":tab/*" let:params let:location>
                <ValFromParams {params} bind:value={tabParams}/>
                {#if params.tab === 'goals'}
                    <ProjectTaskHolder goals={$GOALS.filter(it => !it.gid)}/>
                {:else if params.tab === 'today'}
                    <TodayTaskHolder/>
                {/if}
                <Router>
                    <Route path="task/:id" let:params>
                        <GoalInfoComponent props={params}/>
                    </Route>
                </Router>
            </Route>
        </Router>
    </svelte:fragment>
</HomePage>
