<script lang="ts">

    export let selected: boolean = false;
    export let title: string = "AchiveGoals"
    export let backgroundColor: string = "#f4f4f4";
    export let textColor: string = "#161616"
    export let mainContentColor: string = "#ffffff";
    export let buttonColor: string = "#393939";
    export let skeletonColor: string = "#e5e5e5"


</script>

<svg xml:space="preserve" xmlns:xlink="http://www.w3.org/1999/xlink" width="166" height="77"
     viewBox="0 0 166 77" fill="#db4c3f">
    <defs>
        <rect id="a" width="164" height="75" rx="3" fill="#FFF"></rect>
    </defs>
    <g fill="none" fill-rule="evenodd">
        <g transform="translate(1 1)">
            <mask id="b" fill="#fff">
                <use xlink:href="#a"></use>
            </mask>
            <rect class="_1wUXWJZSJ3NQDOEMTUAyRg==" x="-.5" y="-.5" width="165" height="76" rx="4"
                  fill="{mainContentColor}"></rect>
            <path fill={backgroundColor} mask="url(#b)" d="M0 0h164v30H0z"></path>
            <text mask="url(#b)" font-size="12" fill={textColor}>
                <tspan x="7" y="20">{title}</tspan>
            </text>
            <path d="M11 43a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1a4 4 0 1 1 0-8 4 4 0 0 1 0 8zm11.3-7h47.4l.8.1.4.4.1.8v3.4l-.1.8-.4.4-.8.1H22.3l-.8-.1a1 1 0 0 1-.4-.4l-.1-.8v-3.4l.1-.8.4-.4.8-.1zm58 0h74.4l.8.1.4.4.1.8v3.4l-.1.8-.4.4-.8.1H80.3l-.8-.1a1 1 0 0 1-.4-.4l-.1-.8v-3.4l.1-.8.4-.4.8-.1zm-58 12h23.4l.8.1.4.4.1.8v3.4l-.1.8-.4.4-.8.1H22.3l-.8-.1a1 1 0 0 1-.4-.4l-.1-.8v-3.4l.1-.8.4-.4.8-.1zm34 0h62.4l.8.1.4.4.1.8v3.4l-.1.8-.4.4-.8.1H56.3l-.8-.1a1 1 0 0 1-.4-.4l-.1-.8v-3.4l.1-.8.4-.4.8-.1zM11 56a4 4 0 1 1 0-8 4 4 0 0 1 0 8zm0-1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"
                  fill={skeletonColor} fill-rule="nonzero" mask="url(#b)"></path>
            <rect fill={buttonColor} mask="url(#b)" x="7" y="62" width="26" height="6" rx="1"></rect>
        </g>
        {#if (selected)}
            <path class="lHixqMqlvSc1PxeA6eEPMA=="
                  d="M149.2 67l-4.1-4.1a.5.5 0 0 0-.7 0c-.2.2-.2.5 0 .7l4.8 4.9 8.5-8.4c.2-.2.2-.6 0-.7a.5.5 0 0 0-.7 0l-7.8 7.7z"
                  fill="#db4c3f"></path>
        {/if}
    </g>
</svg>

<style lang="scss">

</style>