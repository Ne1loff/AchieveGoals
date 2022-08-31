<script lang="ts">
    import {createEventDispatcher} from "svelte";

    const dispatch = createEventDispatcher();

    export let open = false;

    const onClick = () => {
        open = !open;
        dispatch('change', open);
    }

</script>

<style>

    :root {
        --own-menu-btn-size: 2rem;
        --own-menu-btn-transition-time: .3s;
        --own-menu-btn-border: 3px solid #000;
        --own-menu-btn-border-radius: 8px;
        --own-menu-btn-background: transparent;
        --own-menu-btn-hover-background: #e1e1e1;

        --own-burger-color: #000;
        --own-burger-border-radius: 5px;
        --own-burger-box-shadow: 0 2px 5px rgba(117, 106, 104, 0.2);
    }

    .menu-btn {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;

        width: var(--own-menu-btn-size);
        height: var(--own-menu-btn-size);

        cursor: pointer;
        transition: all var(--own-menu-btn-transition-time) ease-in-out, background-color 0s ease-in-out;

        border: var(--own-menu-btn-border);
        border-radius: var(--own-menu-btn-border-radius);
        background: var(--own-menu-btn-background);
    }

    .menu-btn:hover {
        background: var(--own-menu-btn-hover-background, #ccc);
    }

    .menu-btn--burger,
    .menu-btn--burger::before,
    .menu-btn--burger::after {
        width: calc(var(--own-menu-btn-size) * .75);
        height: calc(var(--own-menu-btn-size) * .1);

        background: var(--own-burger-color);
        border-radius: var(--own-burger-border-radius);
        box-shadow: var(--own-burger-box-shadow);
        transition: transform var(--own-menu-btn-transition-time) ease-in-out,
        background-color var(--own-menu-btn-transition-time) ease-in-out;
    }

    .menu-btn--burger::before,
    .menu-btn--burger::after {
        content: '';
        position: absolute;
    }

    .menu-btn--burger::before {
        transform: translateY(calc(var(--own-menu-btn-size) * .25 * -1));
    }

    .menu-btn--burger::after {
        transform: translateY(calc(var(--own-menu-btn-size) * .25));
    }

    .menu-btn.open .menu-btn--burger {
        background: transparent;
        box-shadow: none;
    }

    .menu-btn.open .menu-btn--burger::before {
        transform: rotate(45deg);
    }

    .menu-btn.open .menu-btn--burger::after {
        transform: rotate(-45deg);
    }

</style>

<div class="menu-btn" class:open on:click={onClick}>
    <div class="menu-btn--burger"></div>
</div>