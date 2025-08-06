export type TransactionType = 'DEPOSIT' | 'WITHDRAW';

export interface Transaction {
  id: string;
  type: TransactionType;
  amount: number;
  date: string;
}
