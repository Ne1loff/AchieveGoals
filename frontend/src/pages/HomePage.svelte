<script lang="ts">
    import MenuButton from '../components/MenuButton.svelte'
    import Navbar from "../components/Navbar.svelte";
    import Task from "../data/models/Task";
    import {onDestroy, onMount} from "svelte";
    import TaskService from "../services/TaskService";
    import ApiError from "../data/api/ApiError";
    import ToastService, {ErrorMessage} from "../services/ToastService";
    import ApiResponse from "../data/api/ApiResponse";
    import ServiceFactory from "../services/ServiceFactory";
    import LocalStorageService from "../services/LocalStorageService";
    import UserService from "../services/UserService";
    import {USER} from "../data/storage/storage";
    import LoadingScreen from "../components/LoadingScreen.svelte";
    import TopBarMenu from "../components/TopBarMenu.svelte";
    import HomeMenu from "../components/home-components/HomeMenu.svelte";
    import {popover} from "../components/popover/global/Popover";
    import HomeSideBar from "../components/home-components/sidebar/HomeSideBar.svelte";
    import ThemeToggle from "../components/theme/ThemeToggle.svelte";

    export let params: { tab: string };

    let pageIsReady = false;

    let goalService: TaskService;
    let userService: UserService;
    let notificationService: ToastService;
    let localStorageService: LocalStorageService;

    let open: boolean = true;
    let goals: Task[] = [];
    let sideBarWasOpen: boolean;
    let wasCheck: boolean = false;

    $: {
        if (localStorageService && $USER) {
            localStorageService.setSideBarWasOpen($USER.id!!, sideBarWasOpen)
        }
    }

    const onError = (apiError: ApiError) => {
        notificationService.errorFromErrorMessage(new ErrorMessage().fromApiError(apiError));
    }

    const handleResize = () => {
        const autoCloseSideBar = document.body.clientWidth <= 770;
        if (autoCloseSideBar && !wasCheck) {
            open = false;
            wasCheck = true;
        } else if (!autoCloseSideBar) {
            open = sideBarWasOpen;
            wasCheck = false;
        }
    };

    const setUp = () => {
        if (!$USER) {
            userService.getCurrentUser().then(() => {
                if ($USER?.id) {
                    sideBarWasOpen = localStorageService.getSideBarWasOpenOrTrue($USER.id);
                    open = sideBarWasOpen;
                    handleResize();
                    setInterval(() => pageIsReady = true, 200);
                }
            });
        } else {
            if ($USER.id) {
                sideBarWasOpen = localStorageService.getSideBarWasOpenOrTrue($USER.id);
                open = sideBarWasOpen;
                handleResize();
                setInterval(() => pageIsReady = true, 200);
            }
        }
    }

    onMount(() => {
        goalService = ServiceFactory.INSTANCE.taskService;
        userService = ServiceFactory.INSTANCE.userService;
        notificationService = ServiceFactory.INSTANCE.toastService;
        localStorageService = ServiceFactory.INSTANCE.localStorageService;
        goalService.getUserGoals()
            .then((response: Task[]) => goals = response)
            .catch((apiResponse: ApiResponse<ApiError>) => onError(apiResponse.error!!));
        setUp();
    });

    onDestroy(() => {
        popover.destroyAll();
        notificationService.closeAll();
    })

</script>


<svelte:window on:resize={handleResize}/>
<LoadingScreen show={!pageIsReady}/>
<div class="page-inner">
    <Navbar --own-nav-bar-right-margin-right=".5rem">
        <div class="navbar-left" slot="left">
            <MenuButton bind:open
                        on:change={(e) => (sideBarWasOpen = e.detail)}
                        --own-menu-btn-border="none"
                        --own-burger-color="var(--cds-icon-01)"
                        --own-menu-btn-hover-background="var(--cds-layer-hover)"
            />
        </div>
        <svelte:fragment slot="right">
            <ThemeToggle/>
            <TopBarMenu dropdownComponent={{src: HomeMenu}}/>
        </svelte:fragment>
    </Navbar>
    <div class="main-page">
        <HomeSideBar {open} {params}/>
        <div class="main-content-wrapper" class:full-size={!open}>
            <div class="main-content">
                <slot name="content"></slot>
            </div>
        </div>
    </div>
</div>
<slot/>

<style lang="scss">

  @use "sass:math";

  :root {
    --own-nav-bar-height: 4rem;
    --own-nav-bar-width: 100%;
    --own-nav-bar-justify-content: space-between;
    --own-nav-bar-align-items: center;
    --own-nav-bar-align-content: center;

    --own-main-page-footer-height: 2.5rem;
    --own-main-page-footer-width: 100%;

    --own-main-content-height: 100%;

    --own-dev-border: 1px solid #000;
  }

  @function strip-unit($number) {
    @if type-of($number) == 'number' and not unitless($number) {
      @return math.div($number, ($number * 0 + 1));
    }

    @return $number;
  }

  @function calcFluidFontSize($f-min, $f-max, $w-min, $w-max, $units: px) {
    $f-min: strip-unit($f-min);
    $f-max: strip-unit($f-max);
    $w-min: strip-unit($w-min);
    $w-max: strip-unit($w-max);

    $k: math.div(($f-max - $f-min), ($w-max - $w-min));
    $b: $f-min - $k * $w-min;

    $b: $b + $units;

    @return calc(#{$k} * 100vw + #{$b});
  }

  @mixin fluidFontSize($f-min, $f-max, $w-min, $w-max, $fallback: false) {

    font-size: $f-min;

    @media (min-width: $w-min) {
      @if ($fallback) {
        font-size: $fallback;
      }
      font-size: calcFluidFontSize($f-min, $f-max, $w-min, $w-max, px);
    }
    @media (min-width: $w-max) {
      font-size: $f-max;
    }
  }


  .navbar-left {
    margin-left: 8px;
    flex-grow: 1;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    user-select: none;
  }

  .navbar-left span {
    margin-left: 16px;
    @include fluidFontSize(24px, 28px, 480px, 640px, 18px);
  }

  .nav-bar-title {
    flex-grow: 1;

    display: flex;
    justify-content: flex-start;
  }

  .page-inner {
    min-height: 100%;
    width: 100%;

    display: flex;
    flex-direction: column;

    box-sizing: border-box;

    background-color: var(--cds-layer);
  }

  .main-page {
    height: calc(100vh - var(--own-nav-bar-height));
    width: 100%;

    box-sizing: border-box;

    display: flex;
    flex-direction: row;
  }

  .main-content-wrapper {
    min-height: 100%;

    // top right bottom left
    padding: .5rem 1rem 1rem .25rem;
    box-sizing: border-box;

    margin-left: 16rem;

    display: flex;
    flex-direction: row;
    flex-grow: 1;

    transition: padding-left calc(var(--own-sidebar-transition-time) / 2) cubic-bezier(.4, 0, .2, 1),
    margin-left var(--own-sidebar-transition-time) cubic-bezier(.4, 0, .2, 1);
  }

  .main-content-wrapper.full-size {
    padding: .5rem 1rem 1rem 1rem;
    margin-left: 0;
  }

  .main-content {
    min-height: 100%;
    width: 100%;

    background: var(--cds-ui-background);
    overflow: hidden;

    border-radius: 16px;
  }


  .footer {
    --own-main-page-footer-width: var(--own-sidebar-width);
    height: var(--own-main-page-footer-height);
    width: var(--own-main-page-footer-width);

    box-sizing: border-box;
  }

</style>