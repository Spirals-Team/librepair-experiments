package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;

public enum PropertyType {
    Acknowledged {
        public Property create() {
            return new Acknowledged();
        }
    },
    Action {
        public Property create() {
            return new Action();
        }
    },
    Attach {
        public Property create() {
            return new Attach();
        }
    },
    Attendee {
        public Property create() {
            return new Attendee();
        }
    },
    Calscale {
        public Property create() {
            return new Calscale();
        }
    },
    Catagories {
        public Property create() {
            return new Catagories();
        }
    },
    Class {
        public Property create() {
            return new Class();
        }
    },
    Comment {
        public Property create() {
            return new Comment();
        }
    },
    Completed {
        public Property create() {
            return new Completed();
        }
    },
    Contact {
        public Property create() {
            return new Contact();
        }
    },
    Country {
        public Property create() {
            return new Country();
        }
    },
    Created {
        public Property create() {
            return new Created();
        }
    },
    Description {
        public Property create() {
            return new Description();
        }
    },
    Dtend {
        public Property create() {
            return new Dtend();
        }
    },
    Dtstamp {
        public Property create() {
            return new Dtstamp();
        }
    },
    Dtstart {
        public Property create() {
            return new Dtstart();
        }
    },
    Due {
        public Property create() {
            return new Due();
        }
    },
    Duration {
        public Property create() {
            return new Duration();
        }
    },
    Exdate {
        public Property create() {
            return new Exdate();
        }
    },
    Exrule {
        public Property create() {
            return new Exrule();
        }
    },
    Freebusy {
        public Property create() {
            return new Freebusy();
        }
    },
    Geo {
        public Property create() {
            return new Geo();
        }
    },
    Lastmod {
        public Property create() {
            return new Lastmod();
        }
    },
    Location {
        public Property create() {
            return new Location();
        }
    },
    Method {
        public Property create() {
            return new Method();
        }
    },
    Organizer {
        public Property create() {
            return new Organizer();
        }
    },
    Percent {
        public Property create() {
            return new Percent();
        }
    },
    Priority {
        public Property create() {
            return new Priority();
        }
    },
    Prodid {
        public Property create() {
            return new Prodid();
        }
    },
    Rdate {
        public Property create() {
            return new Rdate();
        }
    },
    Recurid {
        public Property create() {
            return new Recurid();
        }
    },
    Related {
        public Property create() {
            return new Related();
        }
    },
    Repeat {
        public Property create() {
            return new Repeat();
        }
    },
    Resources {
        public Property create() {
            return new Resources();
        }
    },
    Rrule {
        public Property create() {
            return new Rrule();
        }
    },
    Rstatus {
        public Property create() {
            return new Rstatus();
        }
    },
    Seq {
        public Property create() {
            return new Seq();
        }
    },
    Status {
        public Property create() {
            return new Status();
        }
    },
    Summary {
        public Property create() {
            return new Summary();
        }
    },
    Transp {
        public Property create() {
            return new Transp();
        }
    },
    Trigger {
        public Property create() {
            return new Trigger();
        }
    },
    Tzid {
        public Property create() {
            return new Tzid();
        }
    },
    Tzname {
        public Property create() {
            return new Tzname();
        }
    },
    Tzoffsetfrom {
        public Property create() {
            return new Tzoffsetfrom();
        }
    },
    Tzoffsetto {
        public Property create() {
            return new Tzoffsetto();
        }
    },
    Tzurl {
        public Property create() {
            return new Tzurl();
        }
    },
    Uid {
        public Property create() {
            return new Uid();
        }
    },
    Url {
        public Property create() {
            return new Url();
        }
    },
    Version {
        public Property create() {
            return new Version();
        }
    };

    public Property create() {
        return null;
    }
}
