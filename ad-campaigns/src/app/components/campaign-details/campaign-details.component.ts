import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CampaignService } from '../../services/campaign.service';
import { Campaign } from '../../models/campaign.model';

@Component({
  selector: 'app-campaign-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './campaign-details.component.html',
  styleUrls: ['./campaign-details.component.scss']
})
export class CampaignDetailsComponent implements OnInit {
  campaign: Campaign | null = null;
  availableTowns: string[] = [];
  availableKeywords: string[] = [];
  isEditing = false;
  newKeyword = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private campaignService: CampaignService
  ) {}

  ngOnInit() {
    this.loadCampaign();
    this.loadTownsAndKeywords();
  }

  loadCampaign() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.campaignService.getCampaign(id).subscribe(
      campaign => this.campaign = campaign
    );
  }

  loadTownsAndKeywords() {
    this.campaignService.getTowns().subscribe(
      towns => this.availableTowns = towns
    );
    this.campaignService.getKeywords().subscribe(
      keywords => this.availableKeywords = keywords
    );
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  addKeyword() {
    if (this.campaign && this.newKeyword && !this.campaign.keywords.includes(this.newKeyword)) {
      this.campaign.keywords.push(this.newKeyword);
      this.newKeyword = '';
    }
  }

  removeKeyword(keyword: string) {
    if (this.campaign) {
      this.campaign.keywords = this.campaign.keywords.filter(k => k !== keyword);
    }
  }

  saveCampaign() {
    if (this.campaign) {
      this.campaignService.updateCampaign(this.campaign.id!, this.campaign).subscribe(() => {
        this.isEditing = false;
        this.loadCampaign();
      });
    }
  }

  deleteCampaign() {
    if (this.campaign && confirm('Are you sure you want to delete this campaign?')) {
      this.campaignService.deleteCampaign(this.campaign.id!).subscribe(() => {
        this.router.navigate(['/campaigns']);
      });
    }
  }
}
