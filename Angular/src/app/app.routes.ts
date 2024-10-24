import { Routes } from '@angular/router';
import { ListUsersComponent } from './list-users/list-users.component';
import { ListHotelsComponent } from './list-hotels/list-hotels.component';
import { ListRoomsComponent } from './list-rooms/list-rooms.component';
import { FormBookingComponent } from './form-booking/form-booking.component';

export const routes: Routes = [
    {path: "users", component:ListUsersComponent},
    {path: "hotels", component:ListHotelsComponent},
    {path: "hotels/:id/rooms", component:ListRoomsComponent},
    {path: "bookingForm", component: FormBookingComponent},
    {path: "**", redirectTo:"users", pathMatch:"full"},
];
