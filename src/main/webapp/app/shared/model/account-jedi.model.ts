export interface IAccountJEDI {
    id?: number;
    accountNum?: number;
    accountName?: string;
    accountBalance?: number;
}

export class AccountJEDI implements IAccountJEDI {
    constructor(public id?: number, public accountNum?: number, public accountName?: string, public accountBalance?: number) {}
}
