import { Routes } from '@angular/router';
import { ListUsersComponent } from './list-users/list-users.component';

export const routes: Routes = [
    {path: "users", component:ListUsersComponent},
    {path: "**", redirectTo:"users", pathMatch:"full"},
];
