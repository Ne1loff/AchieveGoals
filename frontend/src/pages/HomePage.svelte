<script lang="ts">
    import MenuButton from '../components/MenuButton.svelte'
    import Navbar from "../components/Navbar.svelte";
    import Sidebar from "../components/Sidebar.svelte";
    import GoalComponent from "../components/goal-card/GoalComponent.svelte";
    import {navigate} from "svelte-routing";
    import Icon from "@iconify/svelte";
    import Goal from "../data/models/Goal";
    import {onMount} from "svelte";
    import GoalService from "../services/GoalService";
    import ApiError from "../data/api/ApiError";
    import NotificationService, {ErrorMessage} from "../services/NotificationService";
    import ApiResponse from "../data/api/ApiResponse";

    let goalService: GoalService;
    let notificationService: NotificationService;

    let open = true;

    let goals: Goal[] = []; //TODO: doesn't work

    const onError = (apiError: ApiError) => {
        notificationService.errorFromErrorMessage(new ErrorMessage().fromApiError(apiError));
    }

    onMount(() => {
        goalService = GoalService.getInstance();
        notificationService = NotificationService.getInstance();
        goalService.getUserGoals()
            .then((response: Goal[]) => goals = response)
            .catch((apiResponse: ApiResponse<ApiError>) =>
                notificationService.errorFromErrorMessage(new ErrorMessage().fromApiError(apiResponse.data))
            );
    });

</script>

<style lang="scss">

  @use "sass:math";

  :root {
    --own-nav-bar-height: 4rem;
    --own-nav-bar-width: 100%;
    --own-nav-bar-justify-content: space-between;
    --own-nav-bar-align-items: center;
    --own-nav-bar-align-content: center;

    --own-main-page-footer-height: 2rem;
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
    @include fluidFontSize(18px, 28px, 640px, 1280px, 18px);
  }

  .nav-bar-title {
    flex-grow: 1;

    display: flex;
    justify-content: flex-start;
  }

  .navbar-right {
    margin-right: 8px;
  }

  .page-inner {
    min-height: 100%;
    width: 100%;

    display: flex;
    flex-direction: column;

    box-sizing: border-box;

    background-color: #f6f6f6;
  }

  .main-page {
    min-height: calc(100vh - var(--own-nav-bar-height));
    width: 100%;

    box-sizing: border-box;

    display: flex;
    flex-direction: column;
  }

  .main-content {
    height: calc(100% - var(--own-main-page-footer-height));
    width: 100%;

    display: flex;
    flex-direction: row;

    box-sizing: border-box;
  }

  .content {
    width: auto;
    height: var(--own-main-content-height);
    flex-grow: 1;
  }

  .content-inner {
    margin: 0 8px;
    border-radius: 6px;
    background-color: #fff;
    height: 100%;

    display: flex;
    align-items: center;

    box-sizing: border-box;
    box-shadow: 0 2px 6px 0 rgba(0, 0, 0, .1)
  }

  .content-center {
    height: calc(100% - 16px);
    min-width: 30%;
    width: 50%;
    max-width: 70%;
    margin: 8px auto;
  }

  .content-title {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: baseline;
  }

  .content-menu {
    user-select: none;
    cursor: pointer;

    --size: 24px;
    height: var(--size);
    width: var(--size);

    border-radius: 4px;
  }

  .content-menu:hover {
    background-color: #eee;
  }

  .footer {
    height: var(--own-main-page-footer-height);
    width: var(--own-main-page-footer-width);

    box-sizing: border-box;
  }

</style>

<div class="page-inner">
    <Navbar>
        <div class="navbar-left" slot="left">
            <MenuButton bind:open --own-menu-btn-border="none"/>
            <div class="nav-bar-title">
                <span>Achieve Goals</span>
            </div>
        </div>
        <div class="navbar-right" slot="right">
            <Icon size="2rem" icon="bxs:user-circle"/>
        </div>
    </Navbar>
    <div class="main-page">
        <div class="main-content">
            <Sidebar bind:open --own-sidebar-background="#f6f6f6"/>
            <div class="content">
                <div class="content-inner elevation-4">
                    <div class="content-center">
                        <div class="content-title">

                            <div class="content-menu" on:click={() => navigate('./goal/')}>

                            </div>
                        </div>
                        <div>
                            {#each goals.filter(it => it.gid === null) as goal}
                                <GoalComponent bind:goal
                                               subs={goals.filter(it => it.gid === goal.id)}/>
                            {/each}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer"></div>
    </div>
</div>
