import {AdminDeveloperDeleteComponent} from './components/admin-developer-delete/admin-developer-delete.component';
import {AdminDeveloperEditComponent} from './components/admin-developer-edit/admin-developer-edit.component';
import {TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn, TdDataTableService} from '@covalent/core';
import {AdminDeveloperCreateComponent} from './components/admin-developer-create/admin-developer-create.component';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {User} from '../../../../Interfaces/User';
import {UserService} from '../../../../services/user.service';

@Component({
  selector: 'app-admin-developer',
  templateUrl: './admin-developer.component.html',
  styleUrls: ['./admin-developer.component.css']
})
export class AdminDeveloperComponent implements OnInit {

  public users: User[];

  columns: ITdDataTableColumn[] = [
    // { name: 'id',       label: 'ID',        width: 50},
    {name: 'username', label: 'Username', width: {min: 100}},
    {name: 'xp', label: 'XP', width: 50},
    {name: 'gold', label: 'Gold', width: 50},
    {name: 'aboutMe', label: 'About Me', width: {min: 100}},
    {name: 'edit', label: '', width: 120}
  ];

  sortBy = 'username';
  sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Ascending;
  selectedRows: any[] = [];
  filteredData: any[];
  filteredTotal: number;
  searchTerm = '';
  fromRow = 1;
  currentPage = 1;
  pageSize = 50;

  constructor(
    private userService: UserService,
    private dialog: MatDialog,
    private _dataTableService: TdDataTableService  ) {
  }

  ngOnInit() {
    // TODO Subscribe on user change
  }

  getUsers() {
    this.users = this.userService.getUsers();
    this.filter();
  }

  createUser() {
    this.dialog.open(AdminDeveloperCreateComponent, {data: this.users, width: '500px'}).afterClosed().subscribe(user => {
      if (user) {
        this.users.push(user);
        this.userService.setUsers(this.users);
      }
    })
  }

  editUser(user: User) {
    this.dialog.open(AdminDeveloperEditComponent, {data: user, width: '500px'}).afterClosed().subscribe(bool => {
      if (bool) {
        this.userService.setUsers(this.users);
      }
    })
  }

  deleteUser(user: User) {
    this.dialog.open(AdminDeveloperDeleteComponent, {data: user, width: '500px'}).afterClosed().subscribe(bool => {
      if (bool) {
        this.getUsers();
      }
    });
  }

  sort(sortEvent: ITdDataTableSortChangeEvent): void {
    this.sortBy = sortEvent.name;
    this.sortOrder = sortEvent.order;
    this.filter();
  }

  filter(): void {
    let newData: any[] = this.users;
    const excludedColumns: string[] = this.columns
      .filter((column: ITdDataTableColumn) => {
        return ((column.filter === undefined && column.hidden === true) ||
          (column.filter !== undefined && column.filter === false));
      }).map((column: ITdDataTableColumn) => {
        return column.name;
      });
    newData = this._dataTableService.filterData(newData, this.searchTerm, true, excludedColumns);
    this.filteredTotal = newData.length;
    newData = this._dataTableService.sortData(newData, this.sortBy, this.sortOrder);
    newData = this._dataTableService.pageData(newData, this.fromRow, this.currentPage * this.pageSize);
    this.filteredData = newData;
  }

}
