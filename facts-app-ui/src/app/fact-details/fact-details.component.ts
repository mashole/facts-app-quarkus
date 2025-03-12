import { NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatCard, MatCardHeader, MatCardSubtitle } from '@angular/material/card';
import { ActivatedRoute } from '@angular/router';

import { FactService } from '../fact.service';
import { Fact } from '../models/Fact';

@Component({
  selector: 'app-fact-details',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardSubtitle,
    NgIf,
  ],
  templateUrl: './fact-details.component.html',
  styleUrl: './fact-details.component.scss'
})
export class FactDetailsComponent {
  route: ActivatedRoute = inject(ActivatedRoute);
  factService = inject(FactService);
  fact: Fact | undefined;

  constructor() {
    const factId = String(this.route.snapshot.params['id']);
    this.factService.getFactDetails(factId).subscribe({
      next: (fact: Fact) => {
        this.fact = fact;
      },
      error: (error: Fact) => {
        console.error('Error fetching fact:', error);
      }
    });
  }
}
