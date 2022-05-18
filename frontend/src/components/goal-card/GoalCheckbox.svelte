<script lang="ts">
    export let done: boolean = false;
    export let round: boolean = false;

</script>

<style>

    :root {
        --own-checkbox-size: 2rem;
        --own-checbox-border-radius: 5px;

        --own-checkbox-border-color: inerhit;
        --own-checkbox-hover-border-color: rgba(255, 0, 0, 0.15);
        --own-checkbox-done-border-color: red;

        --own-checkbox-bg-color: inerhit;
        --own-checkbox-hover-bg-color: rgba(255, 0, 0, 0.15);
        --own-checkbox-done-bg-color: red;

        --own-check-mark-bg-color: red;
        --own-check-mark-hover-bg-color: rgba(255, 0, 0, 0.7);
        --own-check-mark-done-bg-color: white;
    }

    .check {
        width: var(--own-checkbox-size);
        height: var(--own-checkbox-size);

        display: flex;
        justify-content: center;
        align-items: center;

        background: var(--own-checkbox-bg-color);

        border-radius: var(--own-checbox-border-radius);
        border: 2px solid var(--own-checkbox-border-color);
    }

    .check.round {
        border-radius: 50%;
    }

    .check-mark {
        user-select: none;
        font-size: calc(var(--own-checkbox-size) * .75);
        opacity: 0;

        color: var(--own-check-mark-bg-color);
        transition: opacity .2s ease-in-out;
    }

    .check:hover {
        background: var(--own-checkbox-hover-bg-color);
    }

    .check:hover .check-mark {
        opacity: 1;
        color: var(--own-check-mark-hover-bg-color);
    }

    .check.done {
        background: var(--own-checkbox-done-bg-color);
    }

    .check.done .check-mark {
        opacity: 1;
        color: var(--own-check-mark-done-bg-color);
    }

</style>

<div class="check" class:done class:round on:click={() => done = !done}>
    <div class="check-mark">✔</div>
</div>