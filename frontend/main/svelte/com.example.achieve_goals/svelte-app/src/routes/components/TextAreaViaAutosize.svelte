<script>
    export let value = '';
    export let placeholder = '';
    export let minRows = 1;
    export let maxRows;

    let minHeight;
    let maxHeight

    $: minHeight = `${1 + minRows * 1.2}em`;
    $: maxHeight = maxRows ? `${1 + maxRows * 1.2}em` : `auto`;
</script>

<div class="container" on:click>
	<pre
            aria-hidden="true"
            style="min-height: {minHeight}; max-height: {maxHeight}"
    >{value + '\n'}</pre>

    <textarea bind:value {placeholder}></textarea>
</div>

<style>
    .container {
        position: relative;
        max-height: 175px;
        overflow: hidden;
        overflow-y: auto;
        scrollbar-width: thin;
        scrollbar-color: #787878 #fff;
    }

    .container::-webkit-scrollbar {
        height: 4px;
        width: 4px;
    }
    .container::-webkit-scrollbar-track {
        background: #fff;
    }
    .container::-webkit-scrollbar-thumb {
        background-color: #787878;
        border-radius: 5px;
        border: 3px solid #787878;
    }

    pre, textarea {
        margin: 0;
        padding: 1px;
        font-family: inherit;
        box-sizing: border-box;
        border: none;
        line-height: 1.2;
        font-size: 13px;
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