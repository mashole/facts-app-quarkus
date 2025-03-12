import {Component, inject} from '@angular/core';
import {FactComponent} from "../fact/fact.component";
import {Fact} from "../models/Fact";
import {FactService} from "../fact.service";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-random-fact',
  imports: [
    FactComponent,
    MatButton,
    NgIf
  ],
  templateUrl: './random-fact.component.html',
  styleUrl: './random-fact.component.scss'
})

export class RandomFactComponent {
  fact!: Fact;
  factService = inject(FactService)
  constructor() { }

  ngOnInit(): void {
    const cachedFact = localStorage.getItem('random-fact');
    if (cachedFact) {
      this.fact = JSON.parse(cachedFact) as Fact;
    }

  }
  getFact() {
    this.factService.getAndCacheRandomFact().subscribe({
      next: (fact) => {
        this.fact = fact;
        localStorage.setItem('random-fact', JSON.stringify(fact));
      },
      error: (error) => {
        console.error('Error fetching fact:', error);
      }
    });

  }
}
