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

import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.junit.Assert;
import org.junit.Test;

import com.gitblit.GitBlitException;
import com.gitblit.git.GitblitReceivePack;
import com.gitblit.manager.IGitblit;
import com.gitblit.models.RepositoryModel;
import com.gitblit.models.UserModel;
import com.gitblit.plugin.tbacl.TBACLReceiveHook;

/**
 * Tests the team-branch ACL plugin.
 *
 * @author James Moger
 *
 */
public class Tests extends Assert {

	enum User {
		admin, owner, george, joe, frank
	}

	enum Branch {
		master, green, yellow, red, black
	}

	protected void push(User username, Branch branch, boolean expectSuccess) {

		RepositoryModel repo = new RepositoryModel();
		repo.name = "test.git";
		repo.owners = Arrays.asList(User.owner.name());

		IGitblit gitblit = new MockGitblit();
		try {
			gitblit.updateRepositoryModel(repo.name, repo, false);
		} catch (GitBlitException e) {
		}

		UserModel user = gitblit.getUserModel(username.name());
		Repository db = gitblit.getRepository(repo.name);

		GitblitReceivePack rp = new GitblitReceivePack(gitblit, db, repo, user);

		ObjectId sha1 = ObjectId.fromString("2d291de884b4bb3164fda516ebc8510f757495b7");
		ObjectId sha2 = ObjectId.fromString("42972d830611fa4b1aa2c2c49c824a15e1987597");

		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(sha1, sha2, "refs/heads/" + branch)
				);

		TBACLReceiveHook hook = new TBACLReceiveHook();
		hook.onPreReceive(rp, commands);

		for (ReceiveCommand cmd : commands) {
			assertEquals(cmd.getMessage(), expectSuccess, Result.NOT_ATTEMPTED == cmd.getResult());
		}
	}

	@Test
	public void testUserDefinitions() {

		IGitblit gitblit = new MockGitblit();

		UserModel admin = gitblit.getUserModel(User.admin.name());
		assertTrue(admin.canAdmin());

		UserModel george = gitblit.getUserModel(User.george.name());
		assertTrue(george.isTeamMember(Branch.master.name()));
		assertTrue(george.isTeamMember(Branch.red.name()));
		assertTrue(george.isTeamMember(Branch.yellow.name()));
		assertFalse(george.canAdmin());

		UserModel joe = gitblit.getUserModel(User.joe.name());
		assertFalse(joe.isTeamMember(Branch.master.name()));
		assertTrue(joe.isTeamMember(Branch.red.name()));
		assertTrue(joe.isTeamMember(Branch.yellow.name()));
		assertFalse(joe.canAdmin());

		UserModel frank = gitblit.getUserModel(User.frank.name());
		assertFalse(frank.isTeamMember(Branch.master.name()));
		assertFalse(frank.isTeamMember(Branch.red.name()));
		assertTrue(frank.isTeamMember(Branch.yellow.name()));
		assertFalse(frank.canAdmin());
	}

	@Test
	public void testMasterPush() {
		push(User.admin, Branch.master, true);
		push(User.owner, Branch.master, true);

		push(User.george, Branch.master, true);

		push(User.joe, Branch.master, false);
		push(User.frank, Branch.master, false);
	}

	@Test
	public void testGreenPush() {
		push(User.admin, Branch.green, true);
		push(User.owner, Branch.green, true);

		push(User.george, Branch.green, false);
		push(User.joe, Branch.green, false);

		push(User.frank, Branch.green, true);
	}

	@Test
	public void testYellowPush() {
		push(User.admin, Branch.yellow, true);
		push(User.owner, Branch.yellow, true);
		push(User.george, Branch.yellow, true);
		push(User.joe, Branch.yellow, true);
		push(User.frank, Branch.yellow, true);
	}

	@Test
	public void testRedPush() {
		push(User.admin, Branch.red, true);
		push(User.owner, Branch.red, true);

		push(User.george, Branch.red, true);
		push(User.joe, Branch.red, true);

		push(User.frank, Branch.red, false);
	}

	@Test
	public void testBlackPush() {
		push(User.admin, Branch.black, true);
		push(User.owner, Branch.black, true);

		push(User.george, Branch.black, false);
		push(User.joe, Branch.black, false);
		push(User.frank, Branch.black, false);
	}
}
