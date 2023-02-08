<script lang="ts">
    import AppHeadTitle from "../../AppHeadTitle.svelte";
    import HomeComponentsHolder from "../component-holder/HomeComponentsHolder.svelte";
    import LabelsHolder from "./LabelsHolder.svelte";
</script>

<AppHeadTitle text={'Фильтры и метки'}/>
<HomeComponentsHolder>
    <svelte:fragment slot="head">
        <div class="head-title">
            <h3>Фильтры и метки</h3>
        </div>
    </svelte:fragment>
    <svelte:fragment>
        <div class="section">

        </div>
        <div class="section">
            <LabelsHolder/>
        </div>
    </svelte:fragment>
</HomeComponentsHolder>

<style lang="scss">
  :root {
    --holder-padding-left: 28px;
    --holder-padding-right: 55px;
  }

  .head-title {
    display: flex;
    align-items: baseline;

    & h5 {
      margin-left: .25rem;
      font-weight: inherit;
    }
  }
</style>