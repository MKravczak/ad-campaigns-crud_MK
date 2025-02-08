import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CampaignService } from '../../services/campaign.service';
import { Campaign } from '../../models/campaign.model';

@Component({
  selector: 'app-campaign-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './campaign-list.component.html',
  styleUrls: ['./campaign-list.component.scss']
})
export class CampaignListComponent implements OnInit {
  campaigns: Campaign[] = [];

  constructor(private campaignService: CampaignService) {}

  ngOnInit() {
    this.loadCampaigns();
  }

  loadCampaigns() {
    this.campaignService.getCampaigns().subscribe(
      campaigns => this.campaigns = campaigns
    );
  }
}
