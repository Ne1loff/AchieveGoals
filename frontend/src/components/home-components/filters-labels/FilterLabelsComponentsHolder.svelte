<script lang="ts">
    import Accordion from "../../accordion/Accordion.svelte";
    import Icon from "@iconify/svelte";
    import {links} from "svelte-routing";

    export let title: string;

    let expanded: boolean = true;
</script>

<Accordion bind:open={expanded}>
    <svelte:fragment let:toggle slot="header">
        <div class="action-buttons">
            <button class="collapse-button"
                    on:click={toggle}>
                <span>
                    <span class="icon-holder" data-item-rotate={expanded ? '90' : '0'}>
                        <Icon color="color: var(--cds-icon-01)"
                              height="24" icon="uil:angle-right-b"
                              width="24"
                        />
                    </span>
                    <h2><span>{title}</span></h2>
                </span>
            </button>
            <slot name="action"/>
        </div>
    </svelte:fragment>
    <svelte:fragment>
        <div class="holder" use:links>
            <slot/>
        </div>
    </svelte:fragment>
</Accordion>

<style lang="scss">
  .action-buttons {
    position: relative;
    width: 100%;
    margin: .25rem 0;

    display: flex;
    align-items: center;
    justify-content: space-between;


    color: var(--cds-text-01);
    background-color: inherit;
  }

  .icon-holder {
    transition: transform .1s linear;

    display: flex;
    align-items: center;
    justify-content: center;
  }

  .icon-holder[data-item-rotate="90"] {
    transform: rotate(90deg);
  }

  .action-buttons::after {
    position: absolute;
    left: 0;
    bottom: 0;
    content: "";
    margin-left: 29px;

    width: calc(100% - 29px);
    border: 0;
    border-bottom: 1px solid var(--cds-border-subtle);
  }

  .collapse-button {
    flex-grow: 1;
    height: 40px;

    & h2 {
      text-align: left;
      padding: 6px 2px 5px 0;
      opacity: revert;

      line-height: var(--cds-heading-02-line-height);
      font-size: var(--cds-heading-compact-01-font-size);
      font-weight: var(--cds-heading-compact-01-font-weight);

      margin: 0;
      word-wrap: break-word;
      word-break: break-word;
    }
  }

  .collapse-button span {
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }

  .collapse-button {
    cursor: pointer;

    border: none;
    color: var(--cds-text-01);
    background-color: inherit;
  }

</style>