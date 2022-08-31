
<div class="overlay" on:click></div>

<style>

    :root {
        --own-overlay-bg: rgba(0, 0, 0, 0.02);
    }

    .overlay {
        position: absolute;
        top: 0;
        left: 0;

        width: 100vw;
        height: 100vh;

        background: var(--own-overlay-bg);
        z-index: 900;
    }

</style>