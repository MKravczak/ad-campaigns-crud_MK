import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Campaign } from '../models/campaign.model';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {
  private apiUrl = 'http://localhost:8080/api/campaigns';

  constructor(private http: HttpClient) {}

  getCampaigns(): Observable<Campaign[]> {
    return this.http.get<Campaign[]>(this.apiUrl);
  }

  getCampaign(id: number): Observable<Campaign> {
    return this.http.get<Campaign>(`${this.apiUrl}/${id}`);
  }

  createCampaign(campaign: Campaign): Observable<Campaign> {
    return this.http.post<Campaign>(this.apiUrl, campaign);
  }

  updateCampaign(id: number, campaign: Campaign): Observable<Campaign> {
    return this.http.put<Campaign>(`${this.apiUrl}/${id}`, campaign);
  }

  deleteCampaign(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getTowns(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/towns`);
  }

  getKeywords(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/keywords`);
  }
}
