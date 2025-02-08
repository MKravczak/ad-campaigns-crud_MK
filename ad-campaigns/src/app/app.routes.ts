import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CampaignsComponent } from './campaigns/campaigns.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'campaigns', component: CampaignsComponent }
];

export const appRouting = provideRouter(routes);
