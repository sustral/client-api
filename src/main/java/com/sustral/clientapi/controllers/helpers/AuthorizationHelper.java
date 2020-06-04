package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.dataservices.UserOrganizationRelationshipService;
import com.sustral.clientapi.dataservices.FieldOrganizationRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This helper provides methods that return whether a user has access to certain resources.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class AuthorizationHelper {

    // TODO: This is the most inefficient point in the flow by far. In order to minimize the need for these operations,
    //       which are currently used during every request, JWTs should carry additional claims that declare which orgs
    //       and fields a user can access. If users need to access another user's info (should be uncommon) or an org
    //       or field not included in the JWT (should also be uncommon), they can use these methods. In this case, these
    //       functions will primarily be called by the authentication endpoints. It will also be important to refresh
    //       tokens when a permissions change occurs.

    private final UserOrganizationRelationshipService uorService;
    private final FieldOrganizationRelationshipService forService;

    @Autowired
    public AuthorizationHelper(UserOrganizationRelationshipService uorService, FieldOrganizationRelationshipService forService) {
        this.uorService = uorService;
        this.forService = forService;
    }

    // These methods are written under the assumption that each user is a member of only one or two organizations,
    // and that each organization has at most ~50-100 members. Perhaps in the future, a user/organization can be labeled
    // if they do not follow these assumptions. An entity labeled as such will use a set of methods more efficient
    // for that use case. Given the current business model, there is no need to complicate things yet.

    public Boolean[] canAccessUsers(String currUser, String[] userIds) {

        // Average case of this portion of the method is O(1) since the overwhelming majority
        // of users are only members of a single organization. There is a way to write this method that is GUARANTEED
        // to run in O(n) but that will negatively impact the average case.
        List<UserOrganizationRelationshipEntity> uors = uorService.getManyByUserId(currUser);

        Set<String> accessibleUsers = new HashSet<>();
        for (UserOrganizationRelationshipEntity i: uors) {
            // Can't use parallel streams here
            uorService.getManyByOrganizationId(i.getOrganizationId()).stream().map(s -> accessibleUsers.add(s.getUserId())).close();
        }

        return Arrays.stream(userIds).map(accessibleUsers::contains).toArray(Boolean[]::new);
    }

    public Boolean[] canAccessOrganizations(String currUser, String[] orgIds) {
        Set<String> accessibleOrgs = new HashSet<>();
        uorService.getManyByUserId(currUser).stream().map(s -> accessibleOrgs.add(s.getOrganizationId())).close();

        return Arrays.stream(orgIds).map(accessibleOrgs::contains).toArray(Boolean[]::new);
    }

    public Boolean[] canAccessFields(String currUser, String[] fieldIds) {
        List<UserOrganizationRelationshipEntity> uors = uorService.getManyByUserId(currUser);

        Set<String> accessibleFields = new HashSet<>();
        for (UserOrganizationRelationshipEntity i: uors) {
            forService.getManyByOrganizationId(i.getOrganizationId()).stream().map(s -> accessibleFields.add(s.getFieldId())).close();
        }

        return Arrays.stream(fieldIds).map(accessibleFields::contains).toArray(Boolean[]::new);
    }

    public Boolean[] canAccessScans(String currUser, String[] scanIds) {
        String[] fieldIds = Arrays.stream(scanIds).map(s -> s.split("/")[0]).toArray(String[]::new);
        return canAccessFields(currUser, fieldIds);
    }

    public Boolean[] canAccessFiles(String currUser, String[] fileIds) {
        String[] fieldIds = Arrays.stream(fileIds).map(s -> s.split("/")[0]).toArray(String[]::new);
        return canAccessFields(currUser, fieldIds);
    }

}
