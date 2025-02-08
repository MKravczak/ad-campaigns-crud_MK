import { Component, OnInit, signal } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.scss']
})
export class AccountDetailsComponent implements OnInit {
  balance = signal<number>(0);
  balancePercentage = signal<number>(0);
  fundAmount: number | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getBalance();
  }

  getBalance() {
    this.http.get<number>('http://localhost:8080/api/accounts/balance').subscribe((amount) => {
      this.balance.set(amount);
      this.balancePercentage.set(this.calculateBalancePercentage(amount));
    });
  }

  addFunds() {
    if (!this.fundAmount || this.fundAmount <= 0) return;

    this.http.post(`http://localhost:8080/api/accounts/addFunds?amount=${this.fundAmount}`, {})
      .subscribe(() => {
        this.getBalance();
        this.fundAmount = null;
      });
  }

  calculateBalancePercentage(amount: number): number {
    const maxBalance = 100000;
    return Math.min((amount / maxBalance) * 100, 100);
  }
}
