import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CampaignService } from './campaigns.service';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-campaigns',
  templateUrl: './campaigns.component.html',
  styleUrls: ['./campaigns.component.css']
})
export class CampaignsComponent implements OnInit {
  campaigns: any[] = [];

  constructor(private campaignService: CampaignService) { }

  ngOnInit(): void {
    this.campaignService.getCampaigns().subscribe(data => {
      this.campaigns = data;
    });
  }
}
