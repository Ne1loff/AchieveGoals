<script>
    export let value = '';
    export let placeholder = '';
    export let minRows = 1;
    export let maxRows;

    $: minHeight = `${1 + minRows * 1.2}em`;
    $: maxHeight = maxRows ? `${1 + maxRows * 1.2}em` : `auto`;
</script>

<div class="container">
	<pre
            aria-hidden="true"
            style="min-height: {minHeight}; max-height: {maxHeight}"
    >{value + '\n'}</pre>

    <textarea bind:value {placeholder}></textarea>
</div>

<style>
    .container {
        position: relative;
    }

    pre, textarea {
        margin: 0;
        padding: 1px;
        font-family: inherit;
        box-sizing: border-box;
        border: none;
        line-height: 1.2;
        overflow: hidden;
    }

    textarea {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        resize: none;

    }

    textarea:focus {
        outline: none;
    }

</style>