import {makeAutoObservable, observable} from "mobx";
import {UserType} from "../utils/UserType";
import api from "../utils/api";
import { useQuery } from '@tanstack/react-query';
import React from 'react';

class StaticStore {
    _userTypeCache: UserType[] = [];
    _isLoading: boolean = false;
    _error: string | null = null;

    get error(): string | null {
        return this._error;
    }
    set error(value: string | null) {
        this._error = value;
    }
    get userTypeCache(): UserType[] {
        return this._userTypeCache;
    }
    set userTypeCache(value: UserType[]) {
        this._userTypeCache = value;
    }
    get isLoading(): boolean {
        return this._isLoading;
    }
    set isLoading(value: boolean) {
        this._isLoading = value;
    }
    constructor() {
        makeAutoObservable(this, {
            _userTypeCache: observable,
            _isLoading: observable,
            _error: observable
        });
    }
}

export function useUserTypesQuery() {
  const query = useQuery({
    queryKey: ['userProfiles'],
    queryFn: async () => {
      const res = await api.get("/userProfiles");
      return res.data;
    },
    refetchInterval: 30000,
  });

  React.useEffect(() => {
    if (query.data) {
      staticStore.userTypeCache = query.data;
      staticStore.error = null;
    }
    if (query.error) {
      staticStore.error = (query.error as any).message || 'Unknown error';
    }
  }, [query.data, query.error]);

  return query;
}

const staticStore = new StaticStore();
export default staticStore;