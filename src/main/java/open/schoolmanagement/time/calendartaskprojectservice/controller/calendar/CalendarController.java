/*
   Copyright 2018 Open School Management
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package open.schoolmanagement.time.calendartaskprojectservice.controller.calendar;

import java.util.Collection;
import java.util.Date;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment.Appointment;
import open.schoolmanagement.time.calendartaskprojectservice.service.calendar.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping(path = "/calendar")
public class CalendarController {

  @Autowired
  private CalendarService calendarService;

//  @PostMapping(path = "/appointment/add")
//  public @ResponseStatus
//  Appointment addAppointment(@RequestParam Date start, @RequestParam Date end,
//                             @RequestParam String subject, @RequestParam String description) {
//    return calendarService.createAppointment(start, end, subject, description);
//  }
//
//
//  @GetMapping(path = "/appointment/")
//  Collection<Appointment> listAppointments() {
//
//    //    Person person = Person.builder()
//    //        .personId(123L)
//    //        .emailAddress("abcd@abc.de")
//    //        .firstName("Heinz")
//    //        .lastName("Becker")
//    //        .build();
//
//    return calendarService.listAppointmentsForUser(null);
//  }


}
