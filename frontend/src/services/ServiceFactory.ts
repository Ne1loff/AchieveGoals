import CountryService from "./CountryService";
import GoalService from "./GoalService";
import NotificationService from "./NotificationService";
import SignUIOService from "./SignUIOService";
import UserService from "./UserService";
import Request from "./Request";
import LocalStorageService from "./LocalStorageService";
import PopoverService from "./PopoverService";

class ServiceFactory {
    private static _INSTANCE: ServiceFactory;
    private constructor() { }

    private _popoverService: PopoverService;
    private _localStorageService: LocalStorageService;
    private _countryService: CountryService;
    private _goalService: GoalService;
    private _notificationService: NotificationService;
    private _signUIOService: SignUIOService;
    private _userService: UserService;
    private _request: Request;


    static get INSTANCE(): ServiceFactory {
        return this._INSTANCE ??= new ServiceFactory();
    }

    get popoverService(): PopoverService {
        return this._popoverService ??= new PopoverService();
    }

    get localStorageService(): LocalStorageService {
        return this._localStorageService ??= new LocalStorageService();
    }

    get countryService(): CountryService {
        return this._countryService ??= new CountryService(this.request);
    }

    get goalService(): GoalService {
        return this._goalService ??= new GoalService(this.request);
    }

    get signUIOService(): SignUIOService {
        return this._signUIOService ??= new SignUIOService(this.request, this.userService);
    }

    get userService(): UserService {
        return this._userService ??= new UserService(this.request);
    }

    get notificationService(): NotificationService {
        return this._notificationService ??= new NotificationService();
    }

    get request(): Request {
        return this._request ??= new Request();
    }
}

export default ServiceFactory;