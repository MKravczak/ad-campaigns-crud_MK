import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'campaigns',
    loadComponent: () =>
      import('./components/campaign-list/campaign-list.component')
        .then(m => m.CampaignListComponent)
  },
  {
    path: 'campaigns/create',
    loadComponent: () =>
      import('./components/campaign-create/campaign-create.component')
        .then(m => m.CampaignCreateComponent)
  },
  {
    path: 'campaigns/:id',
    loadComponent: () =>
      import('./components/campaign-details/campaign-details.component')
        .then(m => m.CampaignDetailsComponent)
  },
  {
    path: 'account',
    loadComponent: () =>
      import('./components/account-details/account-details.component')
        .then(m => m.AccountDetailsComponent)
  },
  {
    path: '',
    redirectTo: '/campaigns',
    pathMatch: 'full'
  }
];
