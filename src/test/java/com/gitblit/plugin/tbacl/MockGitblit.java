/*
 * Copyright 2014 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitblit.plugin.tbacl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Repository;

import ro.fortsoft.pf4j.PluginState;
import ro.fortsoft.pf4j.PluginWrapper;
import ro.fortsoft.pf4j.Version;

import com.gitblit.ConfigUserService;
import com.gitblit.Constants.FederationRequest;
import com.gitblit.Constants.FederationToken;
import com.gitblit.GitBlitException;
import com.gitblit.IStoredSettings;
import com.gitblit.manager.IGitblit;
import com.gitblit.manager.IManager;
import com.gitblit.manager.IRuntimeManager;
import com.gitblit.models.FederationModel;
import com.gitblit.models.FederationProposal;
import com.gitblit.models.FederationSet;
import com.gitblit.models.ForkModel;
import com.gitblit.models.GitClientApplication;
import com.gitblit.models.Mailing;
import com.gitblit.models.Metric;
import com.gitblit.models.PluginRegistry.InstallState;
import com.gitblit.models.PluginRegistry.PluginRegistration;
import com.gitblit.models.PluginRegistry.PluginRelease;
import com.gitblit.models.ProjectModel;
import com.gitblit.models.RegistrantAccessPermission;
import com.gitblit.models.RepositoryModel;
import com.gitblit.models.RepositoryUrl;
import com.gitblit.models.SearchResult;
import com.gitblit.models.ServerSettings;
import com.gitblit.models.ServerStatus;
import com.gitblit.models.TeamModel;
import com.gitblit.models.UserModel;
import com.gitblit.tickets.ITicketService;
import com.gitblit.transport.ssh.IPublicKeyManager;
import com.gitblit.transport.ssh.SshKey;

public class MockGitblit implements IGitblit {

	final ConfigUserService userService;

	final Map<String, RepositoryModel> repos = new TreeMap<String, RepositoryModel>();

	public MockGitblit() {
		userService = new ConfigUserService(new File("src/test/resources/test-users.conf"));
	}

	@Override
	public File getGrapesFolder() {
		// TODO Auto-generated method stub
		try {
			return File.createTempFile("test-", null).getParentFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public File getHooksFolder() {
		return getGrapesFolder();
	}

	@Override
	public IStoredSettings getSettings() {
		return new MemorySettings();
	}

	@Override
	public List<String> getAllTeamNames() {
		return userService.getAllTeamNames();
	}

	@Override
	public List<TeamModel> getAllTeams() {
		return userService.getAllTeams();
	}

	@Override
	public TeamModel getTeamModel(String name) {
		return userService.getTeamModel(name);
	}

	@Override
	public List<String> getAllUsernames() {
		return userService.getAllUsernames();
	}

	@Override
	public List<UserModel> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	public UserModel getUserModel(String arg0) {
		return userService.getUserModel(arg0);
	}

	@Override
	public Repository getRepository(String name) {
		DfsRepositoryDescription descr = new DfsRepositoryDescription(name);
		return new InMemoryRepository(descr);
	}

	@Override
	public List<String> getRepositoryTeams(RepositoryModel repository) {
		return userService.getTeamNamesForRepositoryRole(repository.name);
	}

	@Override
	public IManager start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IManager stop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getBaseFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getBootDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileOrFolder(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileOrFolder(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerSettings getSettingsModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeZone getTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDebugMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isServingRepositories() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBaseFolder(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean updateSettings(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disablePlugin(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enablePlugin(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Class<?>> getExtensionClasses(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getExtensions(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginWrapper getPlugin(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PluginWrapper> getPlugins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PluginRegistration> getRegisteredPlugins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PluginRegistration> getRegisteredPlugins(InstallState arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getSystemVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean installPlugin(String arg0, boolean arg1) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PluginRegistration lookupPlugin(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginRelease lookupRelease(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean refreshRegistry(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PluginState startPlugin(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startPlugins() {
		// TODO Auto-generated method stub

	}

	@Override
	public PluginState stopPlugin(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stopPlugins() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean uninstallPlugin(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upgradePlugin(String arg0, String arg1, boolean arg2) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PluginWrapper whichPlugin(Class<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(Mailing arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendHtmlMail(String arg0, String arg1, Collection<String> arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMail(String arg0, String arg1, Collection<String> arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMailToAdministrators(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInternalAccount(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRepositoryRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTeam(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTeamModel(TeamModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserModel(UserModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCookie(UserModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTeamNamesForRepositoryRole(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getUserModel(char[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsernamesForRepositoryRole(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean renameRepositoryRole(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setup(IRuntimeManager arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean updateTeamModel(TeamModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTeamModel(String arg0, TeamModel arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTeamModels(Collection<TeamModel> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserModel(UserModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserModel(String arg0, UserModel arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserModels(Collection<UserModel> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserModel authenticate(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel authenticate(String arg0, SshKey arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel authenticate(HttpServletRequest arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel authenticate(String arg0, char[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCookie(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(HttpServletResponse arg0, UserModel arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCookie(HttpServletResponse arg0, UserModel arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsCredentialChanges(UserModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsDisplayNameChanges(UserModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsEmailAddressChanges(UserModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsTeamMembershipChanges(UserModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsTeamMembershipChanges(TeamModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToCachedRepositoryList(RepositoryModel arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteRepository(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRepositoryModel(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getAllScripts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFork(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForkModel getForkNetwork(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastActivityDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPostReceiveScriptsInherited(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPostReceiveScriptsUnused(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPreReceiveScriptsInherited(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPreReceiveScriptsUnused(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getRepositoriesFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Repository getRepository(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Metric> getRepositoryDefaultMetrics(RepositoryModel arg0, Repository arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRepositoryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepositoryModel getRepositoryModel(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepositoryModel getRepositoryModel(UserModel arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RepositoryModel> getRepositoryModels(UserModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRepositoryUsers(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getStarCount(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RegistrantAccessPermission> getTeamAccessPermissions(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegistrantAccessPermission> getUserAccessPermissions(UserModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegistrantAccessPermission> getUserAccessPermissions(RepositoryModel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasFork(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasRepository(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasRepository(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCollectingGarbage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCollectingGarbage(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIdle(Repository arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetRepositoryListCache() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SearchResult> search(String arg0, int arg1, int arg2, List<String> arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setTeamAccessPermissions(RepositoryModel arg0, Collection<RegistrantAccessPermission> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setUserAccessPermissions(RepositoryModel arg0, Collection<RegistrantAccessPermission> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateConfiguration(Repository arg0, RepositoryModel arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public long updateLastChangeFields(Repository arg0, RepositoryModel arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateRepositoryModel(String arg0, RepositoryModel arg1, boolean arg2) throws GitBlitException {
		// TODO Auto-generated method stub

	}

	@Override
	public ProjectModel getProjectModel(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectModel getProjectModel(String arg0, UserModel arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectModel> getProjectModels(UserModel arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectModel> getProjectModels(List<RepositoryModel> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean acknowledgeFederationStatus(String arg0, FederationModel arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFederate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FederationProposal createFederationProposal(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deletePendingFederationProposal(FederationProposal arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FederationModel getFederationRegistration(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FederationModel> getFederationRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FederationModel> getFederationResultRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FederationSet> getFederationSets(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFederationToken(FederationToken arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFederationToken(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getFederationTokens() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getFederationUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FederationProposal getPendingFederationProposal(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FederationProposal> getPendingFederationProposals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getProposalsFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, RepositoryModel> getRepositories(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean submitFederationProposal(FederationProposal arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateFederationRequest(FederationRequest arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addTeam(TeamModel arg0) throws GitBlitException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(UserModel arg0) throws GitBlitException {
		// TODO Auto-generated method stub

	}

	@Override
	public RepositoryModel fork(RepositoryModel arg0, UserModel arg1) throws GitBlitException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<GitClientApplication> getClientApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPublicKeyManager getPublicKeyManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RepositoryUrl> getRepositoryUrls(HttpServletRequest arg0, UserModel arg1, RepositoryModel arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITicketService getTicketService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reviseTeam(String arg0, TeamModel arg1) throws GitBlitException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reviseUser(String arg0, UserModel arg1) throws GitBlitException {
		// TODO Auto-generated method stub

	}
}