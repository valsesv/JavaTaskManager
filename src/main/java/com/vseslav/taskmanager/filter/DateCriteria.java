package com.vseslav.taskmanager.filter;

import java.time.LocalDate;

public record DateCriteria(LocalDate from, LocalDate to) {
}
