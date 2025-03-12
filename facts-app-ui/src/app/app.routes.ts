import { Routes } from '@angular/router';

import { AdminStatisticsComponent } from './admin-statistics/admin-statistics.component';
import { CachedFactsComponent } from './cached-facts/cached-facts.component';
import { FactDetailsComponent } from './fact-details/fact-details.component';
import { RandomFactComponent } from './random-fact/random-fact.component';

export const routes: Routes = [
  {
    path: '',
    component: RandomFactComponent,
    title: 'Main',
  },
  {
    path: 'facts',
    component: CachedFactsComponent,
    title: 'Facts'
  },
  {
    path: 'fact-details/:id',
    component: FactDetailsComponent,
    title: 'Fact Details',
  },
  {
    path: 'admin/statistics',
    component: AdminStatisticsComponent,
    title: 'Admin Statistics',
  }
];
