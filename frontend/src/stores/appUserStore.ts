import {makeAutoObservable} from "mobx";

class AppUserStore {
    private _isLoading: boolean;
    private _error: string | null;
    private _resultSet: any;

    constructor() {
        makeAutoObservable(this)
    }


    get isLoading(): boolean {
        return this._isLoading;
    }

    set isLoading(value: boolean) {
        this._isLoading = value;
    }

    get error(): string | null {
        return this._error;
    }

    set error(value: string | null) {
        this._error = value;
    }

    get resultSet(): any {
        return this._resultSet;
    }

    set resultSet(value: any) {
        this._resultSet = value;
    }
}

const appUserStore = new AppUserStore();
export default appUserStore;