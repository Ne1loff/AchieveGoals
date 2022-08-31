class LocalStorageService {

    sideBarWasOpenKey(userId: number): string {
        return `__user-${userId}-sidebar-open`;
    }

    getSideBarWasOpenOrTrue(userId: number): boolean {
        const key = this.sideBarWasOpenKey(userId);
        let wasOpen = localStorage.getItem(key);
        if (!wasOpen) {
            return true;
        }
        return Boolean(Number(wasOpen));
    }

    setSideBarWasOpen(userId: number, wasOpen: boolean) {
        localStorage.setItem(this.sideBarWasOpenKey(userId), String(Number(wasOpen)));
    }
}

export default LocalStorageService;