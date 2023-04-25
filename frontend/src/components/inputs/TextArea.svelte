<script lang="ts">
    export let value: string = '';
    export let placeholder: string = '';
    export let minRows: number = 1;
    export let maxRows: number = undefined;

    const getNumberOfRows = (_) => {
        if (value.length === 0) return 0;
        const number = value.split('\n').length;

        if (number <= minRows) return 0;
        if (maxRows && number > maxRows) return maxRows - minRows;
        return number - minRows;
    }

    $: rows = minRows + getNumberOfRows(value);


</script>

<div class="container" on:click
     style="
     --text-area-min-rows: {minRows};
     --text-area-rows: {rows};
     {maxRows ? `--text-area-max-rows : ${maxRows}` : ''}
">
    <textarea bind:value {placeholder}></textarea>
</div>

<style>

    :root {
        --text-area-font-size: var(--cds-label-02-font-size);
        --text-area-font-weight: var(--cds-label-02-font-weight);
        --text-area-line-height: var(--cds-label-02-line-height);

        --text-area-min-rows: 1;
        --text-area-rows: 6;
    }

    .container {
        position: relative;
        max-height: 175px;
        overflow: hidden;
        overflow-y: auto;
        scrollbar-width: thin;
        background: inherit;
        scrollbar-color: var(--cds-active-secondary) inherit;
    }

    .container::-webkit-scrollbar {
        height: .5rem;
        width: .5rem;
    }

    .container::-webkit-scrollbar-track {
        background: inherit;
    }

    .container::-webkit-scrollbar-thumb {
        background-color: var(--cds-active-secondary);
        border-radius: 5px;
        border: 1px solid var(--cds-active-secondary);
    }

    textarea {
        margin: 0;
        padding: 1px;

        box-sizing: border-box;
        border: none;
        overflow: hidden;
        background: inherit;

        font-family: inherit;
        line-height: var(--text-area-line-height);
        font-size: var(--text-area-font-size);
        font-weight: var(--text-area-font-weight);
        color: var(--cds-text-01)
    }

    textarea {
        width: 100%;
        height: calc((var(--text-area-line-height) * var(--text-area-font-size)) * var(--text-area-rows));

        min-height: calc((var(--text-area-line-height) * var(--text-area-font-size)) * var(--text-area-min-rows));
        max-height: calc((var(--text-area-line-height) * var(--text-area-font-size)) * var(--text-area-max-rows, var(--text-area-rows)));

        resize: none;
        color: inherit;
    }

    textarea:focus {
        outline: none;
    }

</style>