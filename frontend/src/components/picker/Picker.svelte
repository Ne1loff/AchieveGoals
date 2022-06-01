<script lang="ts">

</script>


<div class="picker-columns">
    <slot></slot>
    <div class="picker-center-highlight"></div>
</div>


<style>

    :global(:root) {
        --own-picker-item-size: 2rem;
        --own-picker-scroll-padding: 122px;
        --own-picker-item-height: 2rem;
    }


    .picker-columns {
        display: flex;
        justify-content: center;
        overflow: hidden;

        padding: 0;
        height: 100%;
        position: relative;

        text-align: right;
        font-size: var(--cds-quotation-01-font-size);

        -webkit-mask-box-image: linear-gradient(
                to top, transparent,
                transparent 5%,
                var(--cds-field) 20%,
                var(--cds-field) 80%,
                transparent 95%,
                transparent);
    }

    .picker-center-highlight {
        height: var(--own-picker-item-height);
        box-sizing: border-box;
        position: absolute;
        left: 16px;
        right: 16px;
        top: 50%;
        transform: translateY(-50%);
        pointer-events: none;
        background-color: var(--cds-selected-ui);
        border-radius: 8px;
    }

</style>