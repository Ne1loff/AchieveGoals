<script lang="ts">
    import {NotificationType} from "../../data/enums/_enums";
    import Icon from "@iconify/svelte";
    import {ICONS} from "../../resources/constants";
    import dayjs from "dayjs";
    import ToastComponent from "./ToastComponent.svelte";

    export let toastId: number;

    export let notificationType: NotificationType = NotificationType.ERROR;
    export let title: string = "Error";
    export let subtitle: string = "Something went wrong!";
    export let caption: string = dayjs().format('DD MMMM HH:mm');

    const icons = ICONS;

</script>

<ToastComponent lowContrast style="border-left-color: {icons[notificationType].color}"
>
    <svelte:fragment slot="icon">
        <Icon icon={icons[notificationType].name} color={icons[notificationType].color}/>
    </svelte:fragment>
    <svelte:fragment slot="title">
        {title}
    </svelte:fragment>
    <svelte:fragment slot="subtitle">
        {subtitle}
    </svelte:fragment>
    <svelte:fragment slot="caption">
        {caption}
    </svelte:fragment>
</ToastComponent>