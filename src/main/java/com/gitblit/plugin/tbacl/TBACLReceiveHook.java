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
import java.util.Collection;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.fortsoft.pf4j.Extension;

import com.gitblit.Constants;
import com.gitblit.IStoredSettings;
import com.gitblit.extensions.ReceiveHook;
import com.gitblit.git.GitblitReceivePack;
import com.gitblit.models.RepositoryModel;
import com.gitblit.models.UserModel;

/**
 * This hook will enforce Team-Branch ACLs.
 *
 * @author James Moger
 *
 */
@Extension
public class TBACLReceiveHook extends ReceiveHook {

	final String name = "Team-Branch-ACL";

	final Logger log = LoggerFactory.getLogger(getClass());

	public TBACLReceiveHook() {
		super();
	}

	@Override
	public void onPreReceive(GitblitReceivePack receivePack, Collection<ReceiveCommand> commands) {
		final UserModel user = receivePack.getUserModel();
		final RepositoryModel repository = receivePack.getRepositoryModel();
		final IStoredSettings settings = receivePack.getGitblit().getSettings();

		boolean applyToPersonalRepos = settings.getBoolean(Plugin.SETTING_APPLY_TO_PERSONAL_REPOS, false);
		if (repository.isPersonalRepository() && !applyToPersonalRepos) {
			return;
		}

		if (user.canAdmin()) {
			log.info("{}: permitting push from administrator '{}'", name, user.username);
			return;
		}

		if (receivePack.getUserModel().canAdmin(repository)) {
			log.info("{}: permitting push from owner '{}'", name, user.username);
			return;
		}

		for (ReceiveCommand cmd : commands) {
			String reject = String.format("%s: Sorry, '%s' does not have permission to push to '%s'",
					name, user.username, cmd.getRefName());

			if (cmd.getRefName().startsWith(Constants.R_HEADS)) {
				// pushing a branch
				String branch = Repository.shortenRefName(cmd.getRefName());

				// enforce user is member of team named for a branch
				if (!user.isTeamMember(branch)) {
					cmd.setResult(Result.REJECTED_OTHER_REASON, reject);
				} else {
					log.info("{}: permitting push from '{}' to branch '{}'", name, user.username, branch);
				}
			} else {
				// pushing something else
				cmd.setResult(Result.REJECTED_OTHER_REASON, reject);
			}
		}
	}

	@Override
	public void onPostReceive(GitblitReceivePack receivePack, Collection<ReceiveCommand> commands) {
		// NOOP
	}
}