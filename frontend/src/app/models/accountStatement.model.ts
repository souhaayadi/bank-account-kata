import { Transaction } from './transaction.model';

export interface AccountStatement {
  accountId: string;
  balance: number;
  transactions: Transaction[];
}
