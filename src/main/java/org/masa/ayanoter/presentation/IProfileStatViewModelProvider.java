package org.masa.ayanoter.presentation;

import org.masa.ayanoter.dataAccess.User;

public interface IProfileStatViewModelProvider {
    ProfileStatViewModel get(User user);
}
