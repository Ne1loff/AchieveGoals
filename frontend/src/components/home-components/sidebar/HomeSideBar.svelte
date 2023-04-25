<script lang="ts">

    import Sidebar from "../../Sidebar.svelte";
    import HomeSideBarButton from "./HomeSideBarButton.svelte";
    import Icon from "@iconify/svelte";
    import dayjs from "dayjs";
    import {hrefs} from "../../../resources/config";
    import {HomeUrl} from "../../../resources/basicFilter.config";
    import {ACTIVE_HOME_PAGE} from "../../../data/storage/storage";

    export let open: boolean;
    export let params: { tab?: string };

    const isSelected = (href: HomeUrl): boolean => {
        const selected = params?.tab === href;
        if (selected) $ACTIVE_HOME_PAGE = href;
        return selected;
    };


    let homeBtn: boolean;
    let todayBtn: boolean;
    let filterLabelsBtn: boolean;

    $: {
        if (params?.tab) {
            homeBtn = isSelected(HomeUrl.GOALS);
            todayBtn = isSelected(HomeUrl.TODAY);
            filterLabelsBtn = isSelected(HomeUrl.FILTER_LABELS);
        }
    }

</script>

<Sidebar bind:open
         --own-sidebar-background="var(--cds-layer)"
         --own-sidebar-transition-time=".25s"
         --own-sidebar-padding="3rem 0 0 0"
>
    <svelte:fragment slot="top">
        <div class="action-buttons"
             style="--sidebar-btn-padding: 0 .75rem 0 .5rem; --sidebar-btn-margin: .125rem .75rem 0 0">
            <HomeSideBarButton title="Входящие" href={hrefs.home} selected={homeBtn}>
                <Icon slot="icon" icon="icomoon-free:drawer"
                      style="width: 1.5rem; margin-bottom: 3px; height: 1.5rem; padding: 4px; color: var(--cds-inverse-support-04);"/>
            </HomeSideBarButton>
            <HomeSideBarButton title="Сегодня" href={hrefs.basicFilter(HomeUrl.TODAY)}
                               selected={todayBtn}>
                <svelte:fragment slot="icon">
                    <Icon icon="bi:calendar"
                          style="width: 1.5rem; height: 1.5rem; padding: 4px; color: var(--cds-inverse-support-02);"/>
                    <span class="icon-badge">
                        <span>{dayjs().format('DD')}</span>
                    </span>
                </svelte:fragment>
            </HomeSideBarButton>
            <HomeSideBarButton title="Фильтры и метки" href={hrefs.basicFilter(HomeUrl.FILTER_LABELS)}
                               selected={filterLabelsBtn}>
                <Icon slot="icon" icon="ph:diamonds-four"
                      style="width: 1.5rem; transform: rotate(45deg); height: 1.5rem; color: var(--cds-inverse-support-03);"/>
            </HomeSideBarButton>
        </div>
    </svelte:fragment>
    <svelte:fragment slot="center">

    </svelte:fragment>
    <div slot="bottom" class="footer"></div>
</Sidebar>

<style>

    .action-buttons {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-left: 2rem;
    }

    .icon-badge {
        width: 18px;
        height: 18px;

        position: absolute;
        top: 50%;
        left: 50%;

        transform: translate(-50%, -50%);

        color: #058527;
        font-size: 10px;
        font-weight: 400;

        display: flex;
        align-items: center;
        justify-content: center;
    }

    .icon-badge span {
        width: 100%;
        height: 100%;
        transform: translateY(4px);
    }

    :global(a.side-bar-button .icon-badge span) {
        transform: translate(3px, 6px) !important;
    }

</style>