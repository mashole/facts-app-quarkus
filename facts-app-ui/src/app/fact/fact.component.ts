import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';

import { Fact } from '../models/Fact';

@Component({
  selector: 'app-fact',
  imports: [
    MatCardModule, MatButtonModule, RouterLink
  ],
  templateUrl: './fact.component.html',
  styleUrl: './fact.component.scss'
})
export class FactComponent {
  @Input() fact!: Fact;
}
