<div class="container">
  <h2>Employee List</h2>

  <!-- Filter Input Fields -->
  <div class="form-group">
    <label for="firstNameFilter">First Name:</label>
    <input type="text" class="form-control" id="firstNameFilter" [(ngModel)]="filters.firstNameFilter">
  </div>

  <div class="form-group">
    <label for="lastNameFilter">Last Name:</label>
    <input type="text" class="form-control" id="lastNameFilter" [(ngModel)]="filters.lastNameFilter">
  </div>

  <div class="form-group">
    <label for="emailFilter">Email:</label>
    <input type="text" class="form-control" id="emailFilter" [(ngModel)]="filters.emailFilter">
  </div>

  <!-- Apply Filters Button -->
  <button class="btn btn-primary" (click)="applyFilters()">Apply Filters</button>

  <!-- Employee Table with Pagination and Sorting -->
  <table class="table">
    <thead>
      <tr>
        <th (click)="sort('id')">ID <i class="fa" [ngClass]="{'fa-sort': !sortKey['id'], 'fa-sort-asc': sortKey['id'] === 'asc', 'fa-sort-desc': sortKey['id'] === 'desc'}"></i></th>
        <th (click)="sort('firstName')">First Name <i class="fa" [ngClass]="{'fa-sort': !sortKey['firstName'], 'fa-sort-asc': sortKey['firstName'] === 'asc', 'fa-sort-desc': sortKey['firstName'] === 'desc'}"></i></th>
        <th (click)="sort('lastName')">Last Name <i class="fa" [ngClass]="{'fa-sort': !sortKey['lastName'], 'fa-sort-asc': sortKey['lastName'] === 'asc', 'fa-sort-desc': sortKey['lastName'] === 'desc'}"></i></th>
        <th (click)="sort('email')">Email <i class="fa" [ngClass]="{'fa-sort': !sortKey['email'], 'fa-sort-asc': sortKey['email'] === 'asc', 'fa-sort-desc': sortKey['email'] === 'desc'}"></i></th>
      </tr>
    </thead>
    <tbody>
      <tr *ngFor="let employee of pagedEmployees">
        <td>{{ employee.id }}</td>
        <td>{{ employee.firstName }}</td>
        <td>{{ employee.lastName }}</td>
        <td>{{ employee.email }}</td>
      </tr>
    </tbody>
  </table>

  <!-- Pagination Controls -->
  <ul class="pagination">
    <li class="page-item" *ngFor="let page of pages" [class.active]="currentPage === page">
      <a class="page-link" (click)="paginate(page)">{{ page }}</a>
    </li>
  </ul>
</div>












import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[] = [];
  filteredEmployees: Employee[] = [];
  filters = {
    firstNameFilter: '',
    lastNameFilter: '',
    emailFilter: ''
  };

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    const params = new HttpParams()
      .set('firstNameFilter', this.filters.firstNameFilter)
      .set('lastNameFilter', this.filters.lastNameFilter)
      .set('emailFilter', this.filters.emailFilter);

    this.httpClient.get<Employee[]>('/api/employees', { params }).subscribe(data => {
      this.employees = data;
      this.filteredEmployees = data;
    });
  }

  applyFilters() {
    this.fetchData();
  }
}

interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
}










import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployees(
        Pageable pageable,
        @RequestParam(name = "firstNameFilter", required = false) String firstNameFilter,
        @RequestParam(name = "lastNameFilter", required = false) String lastNameFilter,
        @RequestParam(name = "emailFilter", required = false) String emailFilter,
        @RequestParam(name = "sortColumn", defaultValue = "id") String sortColumn,
        @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection
    ) {
        Specification<Employee> spec = Specification.where(null);

        if (firstNameFilter != null && !firstNameFilter.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                builder.like(root.get("firstName"), "%" + firstNameFilter + "%"));
        }

        if (lastNameFilter != null && !lastNameFilter.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                builder.like(root.get("lastName"), "%" + lastNameFilter + "%"));
        }

        if (emailFilter != null && !emailFilter.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                builder.like(root.get("email"), "%" + emailFilter + "%"));
        }

        Sort sorting = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);

        Page<Employee> employees = employeeRepository.findAll(spec, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting));
        return ResponseEntity.ok(employees);
    }
}








<!-- Custom Pagination Controls -->
<ul class="pagination">
  <li class="page-item" *ngFor="let page of pages" [class.active]="currentPage === page">
    <a class="page-link" (click)="paginate(page)">{{ page }}</a>
  </li>
</ul>






paginate(page: number) {
  this.currentPage = page;
  this.fetchData(); // Call a method to fetch data for the selected page
}












<div class="container">
  <h2>Employee List</h2>

  <!-- Filter Input Fields -->
  <div class="form-group">
    <label for="firstNameFilter">First Name:</label>
    <input type="text" class="form-control" id="firstNameFilter" [(ngModel)]="filters.firstNameFilter">
  </div>

  <div class="form-group">
    <label for="lastNameFilter">Last Name:</label>
    <input type="text" class="form-control" id="lastNameFilter" [(ngModel)]="filters.lastNameFilter">
  </div>

  <div class="form-group">
    <label for="emailFilter">Email:</label>
    <input type="text" class="form-control" id="emailFilter" [(ngModel)]="filters.emailFilter">
  </div>

  <!-- Apply Filters Button -->
  <button class="btn btn-primary" (click)="applyFilters()">Apply Filters</button>

  <!-- Employee Table with Pagination and Sorting -->
  <table class="table">
    <thead>
      <tr>
        <th (click)="sort('id')">ID <i class="fa" [ngClass]="{'fa-sort': !sortKey['id'], 'fa-sort-asc': sortKey['id'] === 'asc', 'fa-sort-desc': sortKey['id'] === 'desc'}"></i></th>
        <th (click)="sort('firstName')">First Name <i class="fa" [ngClass]="{'fa-sort': !sortKey['firstName'], 'fa-sort-asc': sortKey['firstName'] === 'asc', 'fa-sort-desc': sortKey['firstName'] === 'desc'}"></i></th>
        <th (click)="sort('lastName')">Last Name <i class="fa" [ngClass]="{'fa-sort': !sortKey['lastName'], 'fa-sort-asc': sortKey['lastName'] === 'asc', 'fa-sort-desc': sortKey['lastName'] === 'desc'}"></i></th>
        <th (click)="sort('email')">Email <i class="fa" [ngClass]="{'fa-sort': !sortKey['email'], 'fa-sort-asc': sortKey['email'] === 'asc', 'fa-sort-desc': sortKey['email'] === 'desc'}"></i></th>
      </tr>
    </thead>
    <tbody>
      <tr *ngFor="let employee of filteredEmployees">
        <td>{{ employee.id }}</td>
        <td>{{ employee.firstName }}</td>
        <td>{{ employee.lastName }}</td>
        <td>{{ employee.email }}</td>
      </tr>
    </tbody>
  </table>

  <!-- Custom Pagination Controls with Bootstrap Styling -->
  <ul class="pagination justify-content-center">
    <li class="page-item" *ngFor="let page of pages" [class.active]="currentPage === page">
      <a class="page-link" (click)="paginate(page)">{{ page }}</a>
    </li>
  </ul>
</div>




sort(column: string) {
  // If the column is already the sorting key, reverse the sorting direction; otherwise, set it to 'asc'.
  this.sortDirection = (column === this.sortColumn && this.sortDirection === 'asc') ? 'desc' : 'asc';
  this.sortColumn = column;
  
  // Fetch data with the updated sorting criteria.
  this.fetchData();
}
