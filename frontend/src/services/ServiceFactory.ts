import CountryService from "./CountryService";
import TaskService from "./TaskService";
import ToastService from "./ToastService";
import AuthenticationService from "./AuthenticationService";
import UserService from "./UserService";
import Request from "./Request";
import LocalStorageService from "./LocalStorageService";
import TaskLabelService from "./TaskLabelService";

class ServiceFactory {
    private static _INSTANCE: ServiceFactory;

    private constructor() {
    }

    private _localStorageService?: LocalStorageService;
    private _countryService?: CountryService;
    private _taskService?: TaskService;

    get taskService(): TaskService {
        return this._taskService ??= new TaskService(this.request);
    }
    private _toastService?: ToastService;

    private _taskLabelService?: TaskLabelService;
    private _userService?: UserService;
    private _request?: Request;


    static get INSTANCE(): ServiceFactory {
        return this._INSTANCE ??= new ServiceFactory();
    }

    get localStorageService(): LocalStorageService {
        return this._localStorageService ??= new LocalStorageService();
    }

    get countryService(): CountryService {
        return this._countryService ??= new CountryService(this.request);
    }

    get taskLabelService(): TaskLabelService {
        return this._taskLabelService ??= new TaskLabelService(this.request);
    }

    private _signUIOService?: AuthenticationService;

    get signUIOService(): AuthenticationService {
        return this._signUIOService ??= new AuthenticationService(this.request, this.userService);
    }

    get userService(): UserService {
        return this._userService ??= new UserService(this.request);
    }

    get toastService(): ToastService {
        return this._toastService ??= new ToastService();
    }

    get request(): Request {
        return this._request ??= new Request();
    }
}

export default ServiceFactory;