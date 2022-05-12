// @ts-ignore
import Country from "../data/models/Country";
import Request from "./Request";

export default class CountryService {
    private static INSTANCE: CountryService;
    private request: Request;

    private constructor() {
        this.request = Request.getInstance();
    }

    static getInstance = () => this.INSTANCE ?? new CountryService();

    async getCountries(): Promise<Country[]> {
        return this.request.get<Country[]>('api/countries/')
            .then((apiResponse) => apiResponse.data as Country[]);
    }
}