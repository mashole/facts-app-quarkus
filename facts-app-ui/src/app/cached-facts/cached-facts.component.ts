import { NgForOf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { FactComponent } from '../fact/fact.component';
import { FactService } from '../fact.service';
import { Fact } from '../models/Fact';

@Component({
  selector: 'app-cached-facts',
  imports: [NgForOf, FactComponent],
  templateUrl: './cached-facts.component.html',
  styleUrl: './cached-facts.component.scss'
})
export class CachedFactsComponent {
  route: ActivatedRoute = inject(ActivatedRoute);
  factService: FactService = inject(FactService);
  facts: Fact[] = [];

  constructor() {
  }

  ngOnInit(): void {
    this.getFacts();
  }

  getFacts() {
    this.factService.getAllFacts().subscribe({
      next: (facts: Fact[]) => {
        this.facts = facts;
      },
      error: (error) => {
        console.error('Error fetching facts:', error);
      }
    });
  }
}
