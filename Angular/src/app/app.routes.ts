import { Routes } from '@angular/router';
import { ListUsersComponent } from './list-users/list-users.component';
import { ListHotelsComponent } from './list-hotels/list-hotels.component';

export const routes: Routes = [
    {path: "users", component:ListUsersComponent},
    {path: "hotels", component:ListHotelsComponent},
    {path: "**", redirectTo:"users", pathMatch:"full"},
];
