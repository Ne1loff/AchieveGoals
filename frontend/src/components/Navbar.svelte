<script lang="ts">

    export let outlined: boolean = false;
    export let outlinedBottom: boolean = false;

</script>

<style lang="scss">

  :root {
    --own-nav-bar-height: 3rem;
    --own-nav-bar-width: 100vw;
    --own-nav-bar-justify-content: space-between;
    --own-nav-bar-align-items: center;
    --own-nav-bar-align-content: center;

    --own-nav-bar-border-size: 2px;
    --own-nav-bar-border-color: #000;

    --own-nav-bar-left-margin-left: 0px;
    --own-nav-bar-left-justify-content: flex-start;
    --own-nav-bar-left-align-items: center;
    --own-nav-bar-left-align-content: center;

    --own-nav-bar-center-margin-left: 0px;
    --own-nav-bar-center-margin-right: 0px;
    --own-nav-bar-center-justify-content: space-between;
    --own-nav-bar-center-align-items: center;
    --own-nav-bar-center-align-content: center;

    --own-nav-bar-right-margin-right: 0px;
    --own-nav-bar-right-justify-content: flex-end;
    --own-nav-bar-right-align-items: center;
    --own-nav-bar-right-align-content: center;
  }

  .nav-bar {
    margin: 0;
    padding: 0;

    height: var(--own-nav-bar-height);
    width: var(--own-nav-bar-width);

    display: flex;
    flex-direction: row;

    justify-content: var(--own-nav-bar-justify-content);
    align-items: var(--own-nav-bar-align-items);
    align-content: var(--own-nav-bar-align-content);
  }

  .nav-bar.outlined {
    border: var(--own-nav-bar-border-size) solid var(--own-nav-bar-border-color);
  }

  .nav-bar.outlinedBottom {
    border-bottom: var(--own-nav-bar-border-size) solid var(--own-nav-bar-border-color);
  }

  .nav-bar-left, .nav-bar-center, .nav-bar-right {
    height: 100%;
    display: flex;
  }

  .nav-bar-left {
    margin-left: var(--own-nav-bar-left-margin-left);
    justify-content: var(--own-nav-bar-left-justify-content);
    align-items: var(--own-nav-bar-left-align-items);
    align-content: var(--own-nav-bar-left-align-content);
  }

  .nav-bar-center {
    margin-left: var(--own-nav-bar-left-margin-left);
    margin-right: var(--own-nav-bar-right-margin-right);
    justify-content: var(--own-nav-bar-center-justify-content);
    align-items: var(--own-nav-bar-center-align-items);
    align-content: var(--own-nav-bar-center-align-content);
  }

  .nav-bar-right {
    margin-right: var(--own-nav-bar-right-margin-right);
    justify-content: var(--own-nav-bar-right-justify-content);
    align-items: var(--own-nav-bar-right-align-items);
    align-content: var(--own-nav-bar-right-align-content);
  }

</style>

<div class="nav-bar" class:outlined class:outlinedBottom>
    {#if $$slots.left}
        <div class="nav-bar-left">
            <slot name="left"></slot>
        </div>
    {/if}
    {#if $$slots.center}
        <div class="nav-bar-center">
            <slot name="center"></slot>
        </div>
    {/if}
    {#if $$slots.right}
        <div class="nav-bar-right">
            <slot name="right"></slot>
        </div>
    {/if}
</div>