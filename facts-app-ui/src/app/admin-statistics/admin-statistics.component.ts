import { Component, inject, ViewChild } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

import { FactService } from '../fact.service';
import { FactAnalitycs } from '../models/FactAnalitycs';


@Component({
  selector: 'app-admin-statistics',
  imports: [
    MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule
  ],
  templateUrl: './admin-statistics.component.html',
  styleUrl: './admin-statistics.component.scss'
})

export class AdminStatisticsComponent {
  displayedColumns: string[] = ['id', 'text', 'source', 'sourceUrl', 'permalink', 'language', 'accessCount'];
  factService = inject(FactService);
  dataSource!: MatTableDataSource<FactAnalitycs>;

  @ViewChild(MatSort) sort!: MatSort;

  constructor() {
    this.factService.getAnalitycsData().subscribe({
      next: (data: FactAnalitycs[]) => {
        this.dataSource = new MatTableDataSource(data);
        this.setSortingRules();
        this.setFilterRules();
      },
      error: (error: any) => {
        console.error('Error fetching analitycs data:', error);
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  private setFilterRules(){
    this.dataSource.filterPredicate = (data: FactAnalitycs, filter: string) => {
      const dataStr = Object.values(data.fact).join(' ').concat(String(data.accessCount)).toLowerCase();
      return dataStr.includes(filter);
    };
  }

  private setSortingRules() {
    this.dataSource.sort = this.sort;
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
      case 'accessCount':
        return item.accessCount;
      case 'id':
        return item.fact.id;
      case 'text':
        return item.fact.text.toLowerCase();
      case 'source':
        return item.fact.source ? item.fact.source.toLowerCase() : '';
      case 'sourceUrl':
        return item.fact.sourceUrl || '';
      case 'language':
        return item.fact.language;
      default:
        return '';
      }
    };
  }
}
