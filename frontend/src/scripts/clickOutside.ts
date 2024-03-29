const clickOutside = (node: HTMLElement) => {

    const handleClick = (event: MouseEvent) => {
        if (event.target instanceof HTMLElement) {
            if (node && !node.contains(event.target) && !event.defaultPrevented) {
                node.dispatchEvent(
                    new CustomEvent('clickOutside')
                )
            }
        }
    }

    document.addEventListener('click', handleClick, true);

    return {
        destroy() {
            document.removeEventListener('click', handleClick, true);
        }
    }
}

export {clickOutside}