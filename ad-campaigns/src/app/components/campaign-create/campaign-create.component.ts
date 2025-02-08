import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CampaignService } from '../../services/campaign.service';

@Component({
  selector: 'app-campaign-create',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './campaign-create.component.html',
  providers: [CampaignService],
  styleUrls: ['./campaign-create.component.scss']
})
export class CampaignCreateComponent implements OnInit {
  campaign = {
    name: '',
    keywords: [] as string[],
    bidAmount: 0,
    campaignFund: 0,
    status: true,
    town: '',
    radius: 0
  };

  availableTowns: string[] = [];
  availableKeywords: string[] = [];
  newKeyword: string = '';

  // Obsługa balansu
  balance = signal<number>(0);
  balancePercentage = signal<number>(0);
  fundAmount: number | null = null;

  successMessage = signal<string | null>(null);

  constructor(
    private campaignService: CampaignService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.loadTownsAndKeywords();
    this.getBalance();
  }

  loadTownsAndKeywords() {
    this.campaignService.getTowns().subscribe(
      towns => this.availableTowns = towns
    );
    this.campaignService.getKeywords().subscribe(
      keywords => this.availableKeywords = keywords
    );
  }

  addKeyword() {
    if (this.newKeyword && !this.campaign.keywords.includes(this.newKeyword)) {
      this.campaign.keywords.push(this.newKeyword);
      this.newKeyword = '';
    }
  }

  removeKeyword(keyword: string) {
    this.campaign.keywords = this.campaign.keywords.filter(k => k !== keyword);
  }

  onSubmit() {
    this.campaignService.createCampaign(this.campaign).subscribe(() => {
      console.log("Campaign created successfully!");
      this.getBalance(); //
      this.showSuccessMessage("Campaign created successfully!"); //
    });

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

  goBack() {
    this.router.navigate(['/campaigns']);
  }

  private showSuccessMessage(message: string) {
    this.successMessage.set(message);
    setTimeout(() => this.successMessage.set(null), 3000);
  }
}
